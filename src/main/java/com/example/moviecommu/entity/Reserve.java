package com.example.moviecommu.entity;

import lombok.Builder;
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

    @Builder
    public Reserve(String method, int amount, String pDate, long scheduleId, String seatId, long userId) {
        this.method = method;
        this.amount = amount;
        this.pDate = pDate;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.userId = userId;
    }
}
