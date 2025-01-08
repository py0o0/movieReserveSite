package com.example.moviecommu.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private int likeCount;

    public void setUserId(String id) {
    }
}

