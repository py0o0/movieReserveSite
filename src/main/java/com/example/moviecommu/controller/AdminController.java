package com.example.moviecommu.controller;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.dto.UserPageResponseDto;
import com.example.moviecommu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/userManage")
    public UserPageResponseDto userManage(int size, int page){
        return userService.getAll(size,page);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(String username, String password) {
        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        userDto.setPassword(password);

        if(userService.adminJoin(userDto)){
            return ResponseEntity.ok("joined");
        }
        else
            return ResponseEntity.badRequest().body("join failed");
    }
}
