package com.example.moviecommu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private String userName;
    private String password;
    private String role;
}
