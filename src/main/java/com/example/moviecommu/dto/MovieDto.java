package com.example.moviecommu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private Long movieId; // 영화 고유 id
    private String title; // 제목
    private String des; // 영화 설명, 줄거리
    private String country; // 국가
    private String director; // 감독
    private String casting; // 캐스팅 (배우 목록)
    private String genre; // 장르
    private float rating; // 평점
    private Date releaseDate; // 개봉일
    private int ageLimit; // 연령 제한
    private int runningTime;
    private int onAir;
    private int headCount;
    private String posterUrl;
}