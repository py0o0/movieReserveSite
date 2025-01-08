package com.example.moviecommu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Builder
@IdClass(ReviewID.class)
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @Column(name = "movie_id", nullable = false)
    private Long movieId;
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Column(name = "up", nullable = false)
    @ColumnDefault("0")
    private int up;

    @Column(name = "down", nullable = false)
    @ColumnDefault("0")
    private int down;
}
