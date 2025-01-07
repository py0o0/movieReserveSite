package com.example.moviecommu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyReserveDto {
    private String seatId;
    private Long scheduleId;
    private String method;
    private int amount;
    private String pDate;

    private String date;
    private String startTime;
    private int movieId;
    private String day;

    private String name;
}
