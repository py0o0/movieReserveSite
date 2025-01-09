package com.example.moviecommu.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class GgimMovieDto {
    private Long movie_id; // 영화 고유 id
    private String title; // 제목
    private String des; // 영화 설명, 줄거리
    private String country; // 국가
    private String director; // 감독
    private String casting; // 캐스팅 (배우 목록)
    private String genre; // 장르
    private float rating; // 평점
    private Date release_date; // 개봉일
    private int age_limit; // 연령 제한
    private int running_time;
    private int on_air;
    private int head_count;
    private String poster_url;
}
