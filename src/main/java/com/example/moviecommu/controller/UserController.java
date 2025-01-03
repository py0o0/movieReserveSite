package com.example.moviecommu.controller;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/join")
    public ResponseEntity<String> join(String username, String password) {
        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        userDto.setPassword(password);

        if(userService.join(userDto)){
            return ResponseEntity.ok("joined");
        }
        else
            return ResponseEntity.badRequest().body("join failed");
    }

}
