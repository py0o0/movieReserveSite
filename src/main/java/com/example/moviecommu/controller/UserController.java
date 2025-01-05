package com.example.moviecommu.controller;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.dto.UserPageResponseDto;
import com.example.moviecommu.service.UserService;
import com.example.moviecommu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserUtil userUtil;
    private final UserService userService;

    @GetMapping("/do")
    public String doUser() {
        return userUtil.getCurrentUserRole();
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
        userDto.setId(username);
        userDto.setPassword(password);

        if(userService.join(userDto)){
            return ResponseEntity.ok("joined");
        }
        else
            return ResponseEntity.badRequest().body("join failed");
    }

    @PostMapping("/user/delete")
    public ResponseEntity<String> deleteUser(String username) {
        if(userService.delete(username)){
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.badRequest().body("delete failed");
    }

    @GetMapping("/follow")
    public ResponseEntity<String> follow(String username) {
        if(userService.follow(username))
            return ResponseEntity.ok("followed");
        return ResponseEntity.badRequest().body("follow failed");
    }

    @GetMapping("/followingList") //사용자 ID를 주면 사용자의 팔로잉 정보 제공
    public UserPageResponseDto getFollowingList(String username,int size, int page) {
        return userService.getFollowingList(username,size, page);
    }

}
