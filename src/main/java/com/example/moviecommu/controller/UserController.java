package com.example.moviecommu.controller;

import com.example.moviecommu.dto.*;
import com.example.moviecommu.service.ReserveService;
import com.example.moviecommu.service.UserService;
import com.example.moviecommu.util.UserUtil;
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
    private final UserUtil userUtil;
    private final UserService userService;
    private final ReserveService reserveService;

    @PostMapping("/test")
    public void test( MultipartFile file) throws IOException {
        String storedFileName=System.currentTimeMillis() + file.getOriginalFilename();
        String savePath = "C:/Users/CSP/mini1/src/main/resources/img/" + storedFileName;
        //"C:/file_upload_test/"
        file.transferTo(new File(savePath));
    }

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

    @PostMapping("user/update") // nickname, birth, phone을 인풋으로 받아 유저 정보 업데이트
    public ResponseEntity<String> update(UserDto userDto) {
        if(userService.update(userDto))
            return ResponseEntity.ok("updated");
        return ResponseEntity.badRequest().body("update failed");
    }

    @PostMapping("/user/delete") //삭제할 유저네임(ID) 인풋
    public ResponseEntity<String> deleteUser(String username) {
        if(userService.delete(username))
            return ResponseEntity.ok("deleted");
        return ResponseEntity.badRequest().body("delete failed");
    }

    @PostMapping("/follow") //팔로잉할 유저네임(ID) 인풋, 이미 팔로잉 되어있을 시 팔로잉 삭제
    public ResponseEntity<String> follow(String username) {
        if(userService.follow(username))
            return ResponseEntity.ok("followed");
        return ResponseEntity.badRequest().body("follow failed");
    }
    @PostMapping("/flwDelete") //팔로워의 유저네임(ID) 인풋으로 받아 삭제
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

    @GetMapping("/userPage") //유저네임(ID) 인풋, 유저 페이지 접속 시 팔로잉,팔로워 수 반환
    public UserPageDto getUserPage(String username){
        return userService.getUserPage(username);
    }

    @GetMapping("/myReserve") // 나의 예매 내역 보기 (상영 날짜 지난 정보는 보여주지 않음)
    public List<MyReserveDto> getMyReserve(){
        return reserveService.getMyReserve();
    }

    @GetMapping("/myReservePrevious") // 나의 예매 내역 보기 (상영 날짜 지난 정보만 보여줌)
    public List<MyReserveDto> getMyReservePrevios(){
        return reserveService.getMyReservePrevios();
    }

}
