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

    @GetMapping("/{postId}") //게시글 상세 조회, 나중에 댓글도 같이 전송
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("update/{postId}") //수정
    public ResponseEntity<?> updatePost(@PathVariable Long postId, String title, String content) {
        return postService.updatePost(postId, title, content);
    }

    @PostMapping("delete/{postId}") //삭제
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @PostMapping("/like")///좋아요
    public void likePost(long postId, String username){
        postService.likePost(postId, username);

    }

    @GetMapping("/list/page") //게시글 전체조회
    public ResponseEntity<?> getPostsByPage(int page, int size) {
        PageRequest pageRequest =  PageRequest.of(page, size);
        return postService.getPostsByPage(pageRequest);
    }

    @GetMapping("/search/title")// 제목으로 검색
    public ResponseEntity<?> searchByTitle(String keyword, int page, int size) {
        System.out.println(keyword);
        PageRequest pageRequest = PageRequest.of(page, size);
        return postService.searchByTitle(keyword, pageRequest);
    }


    @GetMapping("/search/content")// 내용으로 검색
    public ResponseEntity<?> searchByContent(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postService.searchByContent(keyword, pageRequest);
    }

    @GetMapping("/search/username")  // username으로 게시글 불러오기 검색도 가능
    public ResponseEntity<?> searchByUsername(String username, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postService.searchByUsername(username, pageRequest);
    }

    @GetMapping("/search/nickname")  // 닉네임으로 게시글 불러오기 검색도 가능
    public ResponseEntity<?> searchByNickname(String nickname, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postService.searchByNickname(nickname, pageRequest);
    }

    @GetMapping("/search")  // 제목 또는 내용으로 검색
    public ResponseEntity<?> searchPosts(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postService.searchPosts(keyword, pageRequest);
    }
}