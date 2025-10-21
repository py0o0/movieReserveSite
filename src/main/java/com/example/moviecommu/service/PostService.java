package com.example.moviecommu.service;

import com.example.moviecommu.dto.CommentDto;
import com.example.moviecommu.dto.PostDto;
import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.Comment;
import com.example.moviecommu.entity.Post;
import com.example.moviecommu.entity.PostHeart;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.*;
import com.example.moviecommu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PostHeartRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final RedisService redisService;
    private final PostHeartRepository postHeartRepository;

    // 게시글 작성
    public ResponseEntity<?> write(String token, String title, String content){
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        Post post = Post.builder()
                .title(title)
                .content(content)
                .heart(0L)
                .view(0L)
                .user(user)
                .build();
        postRepository.save(post);

        PostDto postDto = PostDto.builder()
                .title(title)
                .content(content)
                .heart(0L)
                .view(0L)
                .email(email)
                .build();
        return ResponseEntity.ok(postDto);
    }

    // 게시글 상세 조회 + 조회수 증가
    public ResponseEntity<?> getPostById(Long postId) {
        Post post = postRepository.findByIdJoinFetch(postId).get();

        // 조회수 증가
        redisService.incrementView(post.getPostId());

        // 게시글 DTO
        PostDto postDto = PostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .heart(post.getHeart())
                .view(post.getView())
                .email(post.getUser().getEmail())
                .build();

        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment comment : post.getComments()){
            CommentDto commentDto = CommentDto.builder()
                    .commentId(comment.getCommentId())
                    .postId(post.getPostId())
                    .content(comment.getContent())
                    .email(comment.getUser().getEmail())
                    .build();
        }
        return ResponseEntity.ok(Map.of(
                "post", postDto,
                "comments", commentDtoList
        ));
    }

    // 게시글 수정
    public ResponseEntity<?> updatePost(String token, Long postId, String title, String content) {
        Post post = postRepository.findById(postId).get();

        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        if (!post.getUser().equals(user)) {
            return ResponseEntity.badRequest().body("작성자만 수정 가능");
        }
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    public ResponseEntity<?> deletePost(String token, Long postId) {
        Post post = postRepository.findById(postId).get();

        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        if (!post.getUser().equals(user)) {
            return ResponseEntity.badRequest().body("작성자만 삭제 가능");
        }

        postRepository.delete(post);
        return ResponseEntity.ok().build();
    }

    // 게시글 좋아요 토글
    public ResponseEntity<?> likePost(String token, long postId) {

        Post post = postRepository.findByIdLock(postId).get(); // 좋아요 수 업데이트 동시성 문제 방지

        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        PostHeart postHeart = postHeartRepository.findByPostAndUser(post, user);

        if(postHeart == null){
            postHeart = PostHeart.builder()
                    .post(post)
                    .user(user)
                    .build();
            postHeartRepository.save(postHeart);
            post.setHeart(post.getHeart() + 1);
        }

        else{
            postHeartRepository.delete(postHeart);
            post.setHeart(post.getHeart() - 1);
        }
        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    // 페이징 조회
    public ResponseEntity<?> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postList = postRepository.findAll(pageable);

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postList){
            PostDto postDto = PostDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .heart(post.getHeart())
                    .view(post.getView())
                    .email(post.getUser().getEmail())
                    .postId(post.getPostId())
                    .build();
            postDtoList.add(postDto);
        }

        return ResponseEntity.ok(postDtoList);
    }

    public ResponseEntity<?> searchByTitle(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postList = postRepository.findByTitleContaining(keyword, pageable);


        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postList){
            PostDto postDto = PostDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .heart(post.getHeart())
                    .view(post.getView())
                    .email(post.getUser().getEmail())
                    .postId(post.getPostId())
                    .build();
            postDtoList.add(postDto);
        }

        return ResponseEntity.ok(postDtoList);
    }

    public ResponseEntity<?> searchByContent(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postList = postRepository.findByContentContaining(keyword, pageable);


        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postList){
            PostDto postDto = PostDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .heart(post.getHeart())
                    .view(post.getView())
                    .email(post.getUser().getEmail())
                    .postId(post.getPostId())
                    .build();
            postDtoList.add(postDto);
        }

        return ResponseEntity.ok(postDtoList);
    }

    public ResponseEntity<?> searchByTitleOrContent(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postList = postRepository.findByTitleOrContentContaining(keyword, pageable);


        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postList){
            PostDto postDto = PostDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .heart(post.getHeart())
                    .view(post.getView())
                    .email(post.getUser().getEmail())
                    .postId(post.getPostId())
                    .build();
            postDtoList.add(postDto);
        }

        return ResponseEntity.ok(postDtoList);
    }

    public ResponseEntity<?> searchByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findByEmail(email);
        Page<Post> postList = postRepository.findByUser(user ,pageable);

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postList){
            PostDto postDto = PostDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .heart(post.getHeart())
                    .view(post.getView())
                    .email(post.getUser().getEmail())
                    .postId(post.getPostId())
                    .build();
            postDtoList.add(postDto);
        }

        return ResponseEntity.ok(postDtoList);
    }

}
