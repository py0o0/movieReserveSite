package com.example.moviecommu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PostDtoMyBatis {
    private long post_id;
    private long user_id;
    private String title;
    private String content;
    private String created;
    private long cnt;
    private long heart;
    private int file_attached;
    private List<String> files;
}
