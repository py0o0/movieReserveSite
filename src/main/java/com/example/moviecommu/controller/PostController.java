package com.example.moviecommu.controller;

import com.example.moviecommu.dto.PostDto;
import com.example.moviecommu.entity.Post;
import com.example.moviecommu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping() //게시글 작성
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String token,
            String title, String content) {
        return postService.write(token, title, content);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("/{postId}") //수정
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String token,
                                        @PathVariable Long postId, String title, String content) {
        return postService.updatePost(token, postId, title, content);
    }

    @DeleteMapping("/{postId}") //삭제
    public ResponseEntity<?> deletePost(@RequestHeader("Authorization") String token,
                           @PathVariable Long postId) {
        return postService.deletePost(token, postId);
    }

    @PostMapping("/{postId}}")//좋아요
    public ResponseEntity<?> likePost(@RequestHeader("Authorization") String token,
                         @PathVariable Long postId){
        return postService.likePost(token, postId);
    }

    @GetMapping() //게시글 전체조회
    public ResponseEntity<?> getPosts(int page, int size) {
        return postService.getPosts(page, size);
    }

    @GetMapping("/search/title")// 제목으로 검색
    public ResponseEntity<?> searchByTitle(String keyword, int page, int size) {
        return postService.searchByTitle(keyword, page, size);
    }


    @GetMapping("/search/content")// 내용으로 검색
    public ResponseEntity<?> searchByContent(String keyword, int page, int size) {
        return postService.searchByContent(keyword, page, size);
    }

    @GetMapping("/search/username")
    public ResponseEntity<?> searchByEmail(String email, int page, int size) {
        return postService.searchByEmail(email, page, size);
    }

    @GetMapping("/search")  // 제목 또는 내용으로 검색
    public ResponseEntity<?> searchByTitleOrContent(String keyword, int page, int size) {
        return postService.searchByTitleOrContent(keyword, page, size);
    }
}