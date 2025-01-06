package com.example.moviecommu.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Reserve {
    private long reservedId;
    private String method;
    private int amount;
    private String pDate;
    private long scheduleId;
    private String seatId;
    private long userId;
}
