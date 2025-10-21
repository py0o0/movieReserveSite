package com.example.moviecommu.controller;

import com.example.moviecommu.dto.*;
import com.example.moviecommu.service.ReserveService;
import com.example.moviecommu.service.UserService;
import com.example.moviecommu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReserveService reserveService;

    @PostMapping("/join")
    public ResponseEntity<?> join(UserDto userDto) {
        return userService.join(userDto);
    }

    @PutMapping("/user") // nickname, birth, phone을 인풋으로 받아 유저 정보 업데이트
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token,
                                         UserDto userDto) {
        return userService.update(token, userDto);
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        return userService.delete(token);
    }

    @PostMapping("/follow") //팔로잉할 유저네임(ID) 인풋, 이미 팔로잉 되어있을 시 팔로잉 삭제
    public ResponseEntity<?> follow(@RequestHeader("Authorization") String token, String email) {
        return userService.follow(token, email);
    }
    @PostMapping("/follower/delete") //팔로워의 유저네임(ID) 인풋으로 받아 삭제
    public ResponseEntity<?> flwerDelete(@RequestHeader("Authorization") String token, String email) {
        return userService.flwerDelete(token, email);
    }

    @GetMapping("/followingList") //사용자 ID를 주면 사용자의 팔로잉 정보 제공
    public ResponseEntity<?> getFollowingList(@RequestHeader("Authorization") String token,
                                          int size, int page) {
        return userService.getFollowingList(token, size, page);
    }

    @GetMapping("/followerList") //사용자의 ID를 주면 사용자의 팔로우 정보 제공
    public ResponseEntity<?> getFollowerList(@RequestHeader("Authorization") String token,
                                         int size, int page) {
        return userService.getFollowerList(token,size, page);
    }

    @GetMapping("/userPage") //유저네임(ID) 인풋, 유저 페이지 접속 시 팔로잉,팔로워 수 반환
    public ResponseEntity<?> getUserPage(String email){
        return userService.getUserPage(email);
    }

    @GetMapping("/myReserve") // 나의 예매 내역 보기 (상영 날짜 지난 정보는 보여주지 않음)
    public List<MyReserveDto> getMyReserve(@RequestHeader("Authorization") String token){
        return reserveService.getMyReserve(token);
    }

    @GetMapping("/myReserve/previous") // 나의 예매 내역 보기 (상영 날짜 지난 정보만 보여줌)
    public List<MyReserveDto> getMyReservePrevious(@RequestHeader("Authorization") String token){
        return reserveService.getMyReservePrevios(token);
    }

    @GetMapping("/user/post")
    public ResponseEntity<?> likePost(String email, int size, int page) {
        return userService.likePost(email,size,page);
    }

    @PostMapping("/ggim")
    public ResponseEntity<String> Ggim(@RequestHeader("Authorization") String token, int movieId){
        return userService.Ggim(token, movieId);
    }

    @DeleteMapping("/ggim")
    public ResponseEntity<String> ggimdelete(@RequestHeader("Authorization") String token, int movieId){
        return userService.ggimdelete(token, movieId);
    }

    @GetMapping("/ggim")
    public ResponseEntity<?> ggimMovie(@RequestHeader("Authorization") String token){
        return userService.ggimMovie(token);
    }


}
