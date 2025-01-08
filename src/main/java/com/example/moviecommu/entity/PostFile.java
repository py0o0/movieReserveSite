package com.example.moviecommu.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PostFile {
    private long postFileId;
    private long postId;
    private String filePath;
}
