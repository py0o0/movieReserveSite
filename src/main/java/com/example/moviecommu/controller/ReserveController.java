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

    @GetMapping("/schedule")
    public List<ScheduleHallDto> schedule(int movieId) {
        return reserveService.schedule(movieId);
    }

    @GetMapping("/reservedSeat")
    public List<ReservedSeatDto> reservedSeat(long scheduleId) {
        return reserveService.reservedSeat(scheduleId);
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(ReserveDto reserveDto){
        if(reserveService.reserve(reserveDto))
            return ResponseEntity.ok("Reserved Successfully");
        return ResponseEntity.badRequest().body("Reservation Failed");
    }
}
