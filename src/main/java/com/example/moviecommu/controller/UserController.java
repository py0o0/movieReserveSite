package com.example.moviecommu.controller;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.dto.UserPageDto;
import com.example.moviecommu.dto.UserPagingDto;
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

    @PostMapping("/join")
    public ResponseEntity<String> join(UserDto userDto) {
        if(userService.join(userDto))
            return ResponseEntity.ok("joined");
        return ResponseEntity.badRequest().body("join failed");
    }

    @PostMapping("user/update")
    public ResponseEntity<String> update(UserDto userDto) {
        if(userService.update(userDto))
            return ResponseEntity.ok("updated");
        return ResponseEntity.badRequest().body("update failed");
    }

    @PostMapping("/user/delete") //삭제할 유저네임 인풋
    public ResponseEntity<String> deleteUser(String username) {
        if(userService.delete(username))
            return ResponseEntity.ok("deleted");
        return ResponseEntity.badRequest().body("delete failed");
    }

    @PostMapping("/follow") //팔로잉할 유저네임 인풋, 이미 팔로잉 되어있을 시 팔로잉 삭제
    public ResponseEntity<String> follow(String username) {
        if(userService.follow(username))
            return ResponseEntity.ok("followed");
        return ResponseEntity.badRequest().body("follow failed");
    }
    @PostMapping("/flwDelete") //팔로워 삭제할 유저네임 인풋
    public ResponseEntity<String> flwerDelete(String username) {
        if(userService.flwerDelete(username))
            return ResponseEntity.ok("deleted");
        return ResponseEntity.badRequest().body("delete failed");
    }

    @GetMapping("/followingList") //사용자 ID를 주면 사용자의 팔로잉 정보 제공
    public UserPagingDto getFollowingList(String username, int size, int page) {
        return userService.getFollowingList(username,size, page);
    }

    @GetMapping("/followerList") //사용자의 ID를 주면 사용자의 팔로우 정보 제공
    public UserPagingDto getFollowerList(String username, int size, int page) {
        return userService.getFollowerList(username,size, page);
    }

    @GetMapping("/userPage") //유저네임 인풋, 유저 페이지 접속 시 팔로잉,팔로워 수 반환
    public UserPageDto getUserPage(String username){
        return userService.getUserPage(username);
    }

}
