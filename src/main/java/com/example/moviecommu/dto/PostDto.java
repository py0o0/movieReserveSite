package com.example.moviecommu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class PostDto {
    private long postId;
    private long userId;
    private String title;
    private String content;
    private String created;
    private long cnt;
    private long heart;
    private int fileAttached;
    private List<String> files;
}
