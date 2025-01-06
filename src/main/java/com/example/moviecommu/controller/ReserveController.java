package com.example.moviecommu.controller;

import com.example.moviecommu.dto.ScheduleHallDto;
import com.example.moviecommu.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
