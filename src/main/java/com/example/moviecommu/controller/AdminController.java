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

    @GetMapping("/userManage") //유저 전체 목록 출력 (페이징) (어드민이 유저 목록 띄워서 관리하는 기능 ex) 강퇴)
    public UserPagingDto userManage(int size, int page){
        return userService.getAll(size,page);
    }

    @PostMapping("/join") //어드민 계정 생성
    public ResponseEntity<String> join(UserDto userDto) {
        if (userService.adminJoin(userDto))
            return ResponseEntity.ok("joined");
        return ResponseEntity.badRequest().body("join failed");
    }

//    @PostMapping("insert/movie")
//    public void insertMovie(MovieDto moviedto) {
//        userService.insertMovie(moviedto);
//    }
}
