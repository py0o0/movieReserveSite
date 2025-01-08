package com.example.moviecommu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long movieId;
    private Long userId;
    private String content;
    private float rating;
    private int up;
    private int down;
}
