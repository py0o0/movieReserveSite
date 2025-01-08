package com.example.movieproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDto {
    private long userId;
    private String title;
    private String content;
    private String created;
    private long cnt;
    private long heart;
}
