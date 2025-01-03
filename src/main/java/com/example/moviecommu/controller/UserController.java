package com.example.moviecommu.controller;

import com.example.moviecommu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/do")
    public String doUser() {
        return "do";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
