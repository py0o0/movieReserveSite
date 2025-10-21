package com.example.moviecommu.service;

import com.example.moviecommu.dto.CommentDto;
import com.example.moviecommu.entity.Comment;
import com.example.moviecommu.entity.Post;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.CommentRepository;
import com.example.moviecommu.repository.PostRepository;
import com.example.moviecommu.repository.UserRepository;
import com.example.moviecommu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    // 댓글 생성 기능
    public ResponseEntity<?> createComment(String token, long postId, String content) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        if(user == null) return ResponseEntity.badRequest().body("User not found");

        Post post = postRepository.findById(postId).get();
        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .build();
        commentRepository.save(comment);
        return ResponseEntity.ok().build();
    }

    // 댓글 수정 기능
    public ResponseEntity<?> updateComment(String token, long commentId, String content) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        Comment comment = commentRepository.findById(commentId).get();
        if(!comment.getUser().equals(user)) {
            return ResponseEntity.badRequest().body("작성자만 수정 가능");
        }
        comment.setContent(content);
        commentRepository.save(comment);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteComment(String token, Long commentId) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        Comment comment = commentRepository.findById(commentId).get();
        if(!comment.getUser().equals(user)) {
            return ResponseEntity.badRequest().body("작성자만 삭제 가능");
        }
        commentRepository.delete(comment);
        return ResponseEntity.ok().build();
    }
}