package com.example.moviecommu.service;

import com.example.moviecommu.dto.CommentDto;
import com.example.moviecommu.dto.PostDto;
import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.Comment;
import com.example.moviecommu.entity.Post;
import com.example.moviecommu.entity.PostFile;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.*;
import com.example.moviecommu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    public void write (String title, String content, List<MultipartFile> files) throws IOException {
        long userId = userUtil.getCurrentUsername();
        Post post = Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();

        if(files == null) {
            post = postRepository.save(post);
            post.setFileAttached(0);
        }
        else {
            post.setFileAttached(1);
            post = postRepository.save(post);
            System.out.println(post.getPostId());
            for(MultipartFile file : files) {
                String storedFileName = System.currentTimeMillis() + file.getOriginalFilename();
                String savePath = "C:/file_upload_test/" + storedFileName;
                file.transferTo(new File(savePath));

                PostFile postFile = new PostFile();
                postFile.setFilePath(savePath);
                postFile.setPostId(post.getPostId());
                postFileRepository.save(postFile);
            }
        }

    }

    // 게시글 상세 조회 및 조회수 증가
    public ResponseEntity<?> getPostById(Long post_id) { // 다듬기 끗 댓글만 추가하셈
        Optional<Post> optionalPost = postRepository.findById(post_id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            PostDto postDto = new PostDto();
            postDto.setPostId(post.getPostId());
            postDto.setUserId(post.getUserId());
            postDto.setTitle(post.getTitle());
            postDto.setContent(post.getContent());
            postDto.setCnt(post.getCnt());
            postDto.setCreated(post.getCreated());
            postDto.setHeart(post.getHeart());
            postDto.setFileAttached(post.getFileAttached());

            if(post.getFileAttached() == 1) {
                List<String> filePath = new ArrayList<>();
                List<PostFile> postFileList = postFileRepository.findByPostId(post_id);

                for(PostFile postFile : postFileList) {
                    filePath.add(postFile.getFilePath());
                }
                postDto.setFiles(filePath);

            }
            User user = userRepository.findByUserId(postDto.getUserId());
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setId(user.getId());

            post.setCnt(post.getCnt() + 1);
            postRepository.save(post);

            List<Comment> commentList = commentRepository.findByPostId(post.getPostId());
            List<CommentDto> commentDtoList = new ArrayList<>();
            List<UserDto> userDtoList = new ArrayList<>();
            for(Comment comment : commentList) {
                CommentDto commentDto = new CommentDto();
                commentDto.setCommentId(comment.getCommentId());
                commentDto.setUserId(comment.getUserId());
                commentDto.setContent(comment.getContent());
                commentDtoList.add(commentDto);

                User cUser = userRepository.findByUserId(comment.getUserId());
                UserDto userDto2 = new UserDto();
                userDto2.setNickname(cUser.getNickname());
                userDto2.setId(cUser.getId());
                userDtoList.add(userDto2);
            }

            return ResponseEntity.ok(Map.of(
                    "post", postDto,
                    "postUser", userDto,
                    "comment", commentDtoList,
                    "commentUser", userDtoList
            ));

        } else {
            return ResponseEntity.badRequest().build();
        }
    }



    public ResponseEntity<?> updatePost(Long id,String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with ID " + id + " not found"));

        long userId = userUtil.getCurrentUsername();
        if(userId != post.getUserId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
        return ResponseEntity.ok(Map.of());
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with ID " + id + " not found"));

        long userId = userUtil.getCurrentUsername();
        String role = userUtil.getCurrentUserRole();
        if(userId != post.getUserId() && !role.equals("ROLE_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        postRepository.delete(post);
    }

    public void likePost(long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with ID " + postId + " not found"));

        User user = userRepository.findById(username);
        Long userId = user.getUserId();

        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("postId",postId);

        if(postLikeRepository.searchLike(map) == 0) {
            postLikeRepository.save(map);
            post.setHeart(post.getHeart() + 1);
        }
        else{
            postLikeRepository.delete(map);
            post.setHeart(post.getHeart() - 1);
        }
        postRepository.save(post);

    }

    public ResponseEntity<?> getPostsByPage(PageRequest pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        List<UserDto> userList = new ArrayList<>();
        for(Post post : postPage.getContent()) {
            User user = userRepository.findByUserId(post.getUserId());
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setId(user.getId());
            userList.add(userDto);
        }

        return ResponseEntity.ok(Map.of(
                "post", postPage,
                "user", userList
        ));
    }

    // 제목으로 검색
    public ResponseEntity<?> searchByTitle(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByTitleContaining(keyword, pageable);
        List<UserDto> userList = new ArrayList<>();
        for(Post post : postPage.getContent()) {
            User user = userRepository.findByUserId(post.getUserId());
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setId(user.getId());
            userList.add(userDto);
        }

        return ResponseEntity.ok(Map.of(
                "post", postPage,
                "user", userList
        ));
    }

    // 내용으로 검색
    public ResponseEntity<?> searchByContent(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByContentContaining(keyword, pageable);
        List<UserDto> userList = new ArrayList<>();
        for(Post post : postPage.getContent()) {
            User user = userRepository.findByUserId(post.getUserId());
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setId(user.getId());
            userList.add(userDto);
        }

        return ResponseEntity.ok(Map.of(
                "post", postPage,
                "user", userList
        ));
    }

    // 제목 또는 내용으로 검색
    public ResponseEntity<?> searchPosts(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        List<UserDto> userList = new ArrayList<>();
        for(Post post : postPage.getContent()) {
            User user = userRepository.findByUserId(post.getUserId());
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setId(user.getId());
            userList.add(userDto);
        }

        return ResponseEntity.ok(Map.of(
                "post", postPage,
                "user", userList
        ));
    }

    public ResponseEntity<?> searchByUsername(String username, PageRequest pageable) {
        User nUser = userRepository.findById(username);
        if (nUser == null) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 유저");
        }
        long userId = nUser.getUserId();
        Page<Post> postPage = postRepository.findByUserId(userId, pageable);
        List<UserDto> userList = new ArrayList<>();
        for(Post post : postPage.getContent()) {
            User user = userRepository.findByUserId(post.getUserId());
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setId(user.getId());
            userList.add(userDto);
        }

        return ResponseEntity.ok(Map.of(
                "post", postPage,
                "user", userList
        ));
    }
}
