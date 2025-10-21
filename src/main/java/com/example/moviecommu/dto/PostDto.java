package com.example.moviecommu.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long postId;
    private String email;
    private String title;
    private String content;
    private String created;
    private long view;
    private long heart;
}
