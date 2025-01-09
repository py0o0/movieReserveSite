package com.example.moviecommu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name="post")
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    private long userId;
    private String title;
    private String content;
    private String created;
    private long cnt;
    private long heart;

    private int fileAttached;

    @Builder
    public Post(long userId, String title, String content, long cnt, long heart, int fileAttached) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.cnt = cnt;
        this.heart = heart;
        this.fileAttached = fileAttached;
    }

    @PrePersist
    public void prePersist() {
        // 현재 날짜를 "yyyy-MM-dd" 형식으로 설정
        this.created = LocalDate.now().toString();
    }
}
