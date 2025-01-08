package com.example.moviecommu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", unique = true, nullable = false)
    private Long movieId; // 영화 고유 id

    @Column(name = "title", nullable = false)
    private String title; // 제목

    @Column(name = "des")
    private String des; // 영화 설명, 줄거리

    @Column(name = "country", nullable = false)
    private String country; // 국가

    @Column(name = "director", nullable = false)
    private String director; // 감독

    @Column(name = "casting", nullable = false)
    private String casting; // 캐스팅 (배우 목록)

    @Column(name = "genre", nullable = false)
    private String genre; // 장르

    @Column(name = "rating", nullable = false)
    private float rating; // 평점

    @Column(name = "release_date", nullable = false)
    private Date releaseDate; // 개봉일

    @Column(name = "age_limit", nullable = false)
    private int ageLimit; // 연령 제한

    @Column(name = "running_time", nullable = false)
    private int runningTime;

    @Column(name = "on_air", nullable = false)
    private int onAir;

    @Column(name = "head_count", nullable = false)
    private int headCount;

    @Column(name = "poster_url", nullable = false)
    private String posterUrl;
}
