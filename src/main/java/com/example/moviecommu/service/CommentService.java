package com.example.moviecommu.service;

import com.example.moviecommu.dto.CommentDto;
import com.example.moviecommu.entity.Comment;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.CommentRepository;
import com.example.moviecommu.repository.UserRepository;
import com.example.moviecommu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserUtil userUtil;
    private final UserRepository userRepository;

    // 댓글 생성 기능
    public void createComment(String content, Long postId, String username) {
       Comment comment = new Comment();
       comment.setContent(content);
       comment.setPostId(postId);
        User user = userRepository.findById(username);
        Long userId = user.getUserId();
        comment.setUserId(userId);
        commentRepository.save(comment);
    }

    // 댓글 수정 기능
    public CommentDto updateComment(Long id, String content) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        comment.setContent(content);
        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment);
    }

    // 댓글 삭제 기능
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    // DTO를 엔티티로 변환하는 유틸리티 메서드
    private CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setContent(comment.getContent());
        dto.setPostId(comment.getPostId());
        dto.setUserId(comment.getUserId());
        return dto;
    }
}