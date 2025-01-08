package com.example.movieproject.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private long boardId;
    private long userId;
    private String title;
    private String content;
    private String created;
    private long cnt;
    private long heart;
    @Builder
    public Post(long userId, String title, String content, long cnt, long heart) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.cnt = cnt;
        this.heart = heart;
    }
}
