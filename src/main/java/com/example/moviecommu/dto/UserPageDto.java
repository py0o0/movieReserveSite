package com.example.moviecommu.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPageDto {
    private long following;
    private long followers;

    @Builder
    public UserPageDto(long following, long followers) {
        this.following = following;
        this.followers = followers;
    }
}
