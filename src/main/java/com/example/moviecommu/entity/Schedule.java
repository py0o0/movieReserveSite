package com.example.moviecommu.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Schedule {
    private long scheduleId;
    private long hallId;
    private int movieId;
    private String startTime;
    private String date;
    private String day;

}
