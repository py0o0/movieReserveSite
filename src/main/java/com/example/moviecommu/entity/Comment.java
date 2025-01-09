package com.example.moviecommu.entity;

import jakarta.persistence.*;
import lombok.Data;

// 댓글 엔티티 - 데이터베이스의 댓글 테이블과 매핑
@Entity
@Table(name = "comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    private Long postId;

    private Long userId;

}

