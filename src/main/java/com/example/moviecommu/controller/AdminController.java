package com.example.moviecommu.controller;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.dto.UserPagingDto;
import com.example.moviecommu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/userManage")
    public UserPagingDto userManage(int size, int page){
        return userService.getAll(size,page);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(UserDto userDto) {
        if (userService.adminJoin(userDto))
            return ResponseEntity.ok("joined");
        return ResponseEntity.badRequest().body("join failed");
    }
}
