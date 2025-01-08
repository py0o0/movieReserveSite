package com.example.moviecommu.service;

import com.example.moviecommu.dto.PostDto;
import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.Post;
import com.example.moviecommu.entity.PostFile;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.PostFileRepository;
import com.example.moviecommu.repository.PostLikeRepository;
import com.example.moviecommu.repository.PostRepository;
import com.example.moviecommu.repository.UserRepository;
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

    // 다듬기 끝
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

//    // 게시글 목록 조회
//    public List<PostDto> getAll() {
//        List<Post> postList = postRepository.findAll();
//        List<PostDto> postDtoList = new ArrayList<>();
//        for (Post post : postList) {
//            PostDto postDto = new PostDto();
//            postDto.setTitle(post.getTitle());
//            postDto.setContent(post.getContent());
//            postDto.setUserId(post.getUserId());
//            postDto.setCnt(post.getCnt());
//            postDto.setCreated(post.getCreated());
//            postDto.setHeart(post.getHeart());
//            postDtoList.add(postDto);
//        }
//        return postDtoList;
//    }

    // 게시글 상세 조회 및 조회수 증가
    public ResponseEntity<?> getPostById(Long post_id) { // 다듬기 끗 댓글만 추가하셈
        Optional<Post> optionalPost = postRepository.findById(post_id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // DTO 변환
            PostDto postDto = new PostDto();
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

            // 조회수 증가
            post.setCnt(post.getCnt() + 1);
            postRepository.save(post);

            return ResponseEntity.ok(Map.of(
                    "post", postDto,
                    "user", userDto
            ));

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시글 수정
    public ResponseEntity<?> updatePost(Long id,String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with ID " + id + " not found"));

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
        return ResponseEntity.ok(Map.of());
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with ID " + id + " not found"));
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

    // 페이징 처리
    public Page<PostDto> getPostsByPage(PageRequest pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(this::convertToDto);
    }

    // 제목으로 검색
    public Page<PostDto> searchByTitle(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByTitleContaining(keyword, pageable);
        return postPage.map(this::convertToDto);
    }

    // 내용으로 검색
    public Page<PostDto> searchByContent(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByContentContaining(keyword, pageable);
        return postPage.map(this::convertToDto);
    }

    // 제목 또는 내용으로 검색
    public Page<PostDto> searchPosts(String keyword, PageRequest pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 비워둘 수 없습니다.");
        }
        Page<Post> postPage = postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        return postPage.map(this::convertToDto);
    }

    // Post 엔티티를 PostDto로 변환하는 메서드
    private PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setUserId(post.getUserId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCnt(post.getCnt());
        dto.setCreated(post.getCreated());
        dto.setHeart(post.getHeart());
        return dto;
    }



}
