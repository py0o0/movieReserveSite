package com.example.moviecommu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post") // Comment에서 Post 필드 이름
    private List<Comment> comments;

    private String title;
    private String content;
    private long view;
    private String createdAt;
    private long heart;

    @PrePersist
    public void prePersist() {
        // 현재 날짜를 "yyyy-MM-dd" 형식으로 설정
        this.createdAt = LocalDate.now().toString();
    }
}
