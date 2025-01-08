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

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Void> createPost(@RequestBody PostDto postDto) {
        postService.write(postDto);
        return ResponseEntity.ok().build();
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAll());
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        try {
            PostDto postDto = postService.getPostById(id);
            return ResponseEntity.ok(postDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 페이징 처리
    @GetMapping("/list/page")
    public ResponseEntity<Page<PostDto>> getPostsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "boardId"));
        return ResponseEntity.ok(postService.getPostsByPage(pageRequest));
    }

    // 제목으로 검색
    @GetMapping("/search/title")
    public ResponseEntity<Page<PostDto>> searchByTitle(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.searchByTitle(keyword, pageRequest));
    }

    // 내용으로 검색
    @GetMapping("/search/content")
    public ResponseEntity<Page<PostDto>> searchByContent(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.searchByContent(keyword, pageRequest));
    }

    // 제목 또는 내용으로 검색
    @GetMapping("/search")
    public ResponseEntity<Page<PostDto>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.searchPosts(keyword, pageRequest));
    }
}