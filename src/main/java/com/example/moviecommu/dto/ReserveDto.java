package com.example.moviecommu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReserveDto {
    private String method;
    private int amount;
    private String pDate;
    private long scheduleId;
    private String seatId;
}
