package com.example.moviecommu.entity;

import jakarta.persistence.*;
import lombok.Data;

// 댓글 엔티티 - 데이터베이스의 댓글 테이블과 매핑
@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "like_count")
    private int likeCount = 0;
}

