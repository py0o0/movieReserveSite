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
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/write") //게시글 작성 이미지 파일들 첨부 가능
    public ResponseEntity<Void> createPost(String title, String content, List<MultipartFile> files) throws IOException {
        postService.write(title, content, files);
        return ResponseEntity.ok().build();
    }

    // 게시글 목록 조회@GetMapping("/list")
    ////    public ResponseEntity<List<PostDto>> getAllPosts() {
    ////        return ResponseEntity.ok(postService.getAll());
    ////    }
//

    // 게시글 상세 조회
    @GetMapping("/{id}") //게시글 상세 조회, 나중에 댓글도 같이 전송
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // 게시글 수정
    @PutMapping("/{id}") //다듬기 완
    public ResponseEntity<?> updatePost(@PathVariable Long id, String title, String content) {
        return postService.updatePost(id, title, content);
    }

    // 게시글 삭제
    @PostMapping("/{id}") //다듬기 완
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    // 페이징 게시글 전체 조회
    @GetMapping("/list/page") //이건 됨 근데 유저정보를 같이 반환하는걸 모르겟음 한신님하고 토론
    public ResponseEntity<Page<PostDto>> getPostsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "postId"));
        return ResponseEntity.ok(postService.getPostsByPage(pageRequest));
    }

    // 제목으로 검색
    @GetMapping("/search/title") //이 밑으로 쭉안됨 내일 물어보고 다시 짜든가 해야될듯
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