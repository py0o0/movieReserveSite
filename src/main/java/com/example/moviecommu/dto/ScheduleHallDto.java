package com.example.moviecommu.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleHallDto {
    private Long scheduleId;
    private Long hallId;
    private int movieId;
    private int session;
    private String startTime;
    private String date;
    private String day;

    private int price;
    private String name;

    @Builder
    public ScheduleHallDto(Long scheduleId, Long hallId, int movieId, int session, String startTime, String date, String day, int price, String name) {
        this.scheduleId = scheduleId;
        this.hallId = hallId;
        this.movieId = movieId;
        this.session = session;
        this.startTime = startTime;
        this.date = date;
        this.day = day;
        this.price = price;
        this.name = name;
    }
}
