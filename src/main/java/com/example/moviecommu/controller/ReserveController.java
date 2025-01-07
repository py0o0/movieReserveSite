package com.example.moviecommu.controller;

import com.example.moviecommu.dto.ReserveDto;
import com.example.moviecommu.dto.ReservedSeatDto;
import com.example.moviecommu.dto.ScheduleHallDto;
import com.example.moviecommu.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveService reserveService;

    @GetMapping("/schedule") //인풋으로 받은 movieId의 스케쥴 정보를 오늘 날짜 기준 5일 후 까지 출력
    public List<ScheduleHallDto> schedule(int movieId) {
        return reserveService.schedule(movieId);
    }

    @GetMapping("/reservedSeat") //선택한 스케쥴에 이미 예매된 좌석 출력
    public List<ReservedSeatDto> reservedSeat(long scheduleId) {
        return reserveService.reservedSeat(scheduleId);
    }

    @PostMapping("/reserve") //예매
    public ResponseEntity<String> reserve(ReserveDto reserveDto){
        if(reserveService.reserve(reserveDto))
            return ResponseEntity.ok("Reserved Successfully");
        return ResponseEntity.badRequest().body("Reservation Failed");
    }

    @PostMapping("/reserve/delete") //예매취소
    public ResponseEntity<String> reserveDelete(String seatId, long scheduleId){
        if(reserveService.reserveDelete(seatId, scheduleId))
            return ResponseEntity.ok("ReservedDelete Successfully");
        return ResponseEntity.badRequest().body("ReservedDelete Failed");
    }
}
