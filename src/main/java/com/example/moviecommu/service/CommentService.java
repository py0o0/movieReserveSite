package com.example.moviecommu.service;

import com.example.moviecommu.dto.CommentDto;
import com.example.moviecommu.entity.Comment;
import com.example.moviecommu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    // 댓글 생성 기능
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);
        Comment savedComment = commentRepository.save(comment);
        return convertToDto(savedComment);
    }

    // 특정 댓글 조회 기능
    public CommentDto getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return convertToDto(comment);
    }

    // 댓글 수정 기능
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(commentDto.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment);
    }

    // 댓글 삭제 기능
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    // 댓글 좋아요 기능
    public void likeComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);
    }

    // DTO와 엔티티 간 변환 메서드
    private Comment convertToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        // Post와 User 설정 로직 추가 필요
        return comment;
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setPostId(comment.getPost().getId());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setLikeCount(comment.getLikeCount());
        return commentDto;
    }
}
