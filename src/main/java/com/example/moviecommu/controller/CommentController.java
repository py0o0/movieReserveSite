package com.example.moviecommu.controller;

import com.example.moviecommu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // 댓글 생성 기능
    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(@RequestHeader("Authorization")String token,
                              @PathVariable Long postId,
                              String content) {
        return commentService.createComment(token, postId, content);
    }

    // 댓글 수정 기능
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@RequestHeader("Authorization")String token,
            @PathVariable Long commentId, String content) {
        return commentService.updateComment(token, commentId, content);
    }

    // 댓글 삭제 기능
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestHeader("Authorization")String token,
                                              @PathVariable Long commentId) {
        return commentService.deleteComment(token, commentId);
    }
}
