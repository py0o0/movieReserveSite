package com.example.moviecommu.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long userId;
    private String email;
    private String password;
    private String role;
    private String nickname;
    private String phone;
    private String birth;
}
