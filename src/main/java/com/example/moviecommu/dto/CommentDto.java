package com.example.moviecommu.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long commentId;
    private String content;
    private Long postId;
    private Long userId;
}

