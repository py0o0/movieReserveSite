package com.example.moviecommu.service;

import com.example.moviecommu.dto.ScheduleHallDto;
import com.example.moviecommu.entity.Hall;
import com.example.moviecommu.entity.Schedule;
import com.example.moviecommu.repository.HallRepository;
import com.example.moviecommu.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final HallRepository hallRepository;

    public List<ScheduleHallDto> schedule(int movieId) {

        //영화 Id 확인해서 예매가능한지 로직 추가 널 반환하게
        List<ScheduleHallDto> scheduleHallDtos = new ArrayList<>();
        List<Schedule> scheduleInfo = reserveRepository.findByMovieId(movieId);

        for(Schedule schedule : scheduleInfo) {
            Hall hall = hallRepository.findByHallId(schedule.getHallId());
            ScheduleHallDto scheduleHallDto = ScheduleHallDto.builder()
                    .date(schedule.getDate())
                    .day(schedule.getDay())
                    .hallId(schedule.getHallId())
                    .movieId(schedule.getMovieId())
                    .name(hall.getName())
                    .price(hall.getPrice())
                    .session(schedule.getSession())
                    .scheduleId(schedule.getScheduleId())
                    .startTime(schedule.getStartTime())
                    .build();
            scheduleHallDtos.add(scheduleHallDto);
        }
        return scheduleHallDtos;
    }
}
