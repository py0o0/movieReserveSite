package com.example.moviecommu.controller;

import com.example.moviecommu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // 댓글 생성 기능
    @PostMapping("/newcomment")
    public void createComment(String content, Long postId, String username) {
        commentService.createComment(content, postId, username);
    }

    // 댓글 수정 기능
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestParam("content") String content
    ) {
        commentService.updateComment(commentId, content);
        // 댓글 수정 로직
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제 기능
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
