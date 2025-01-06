package com.example.moviecommu.service;

import com.example.moviecommu.dto.ReserveDto;
import com.example.moviecommu.dto.ReservedSeatDto;
import com.example.moviecommu.dto.ScheduleHallDto;
import com.example.moviecommu.entity.Hall;
import com.example.moviecommu.entity.Reserve;
import com.example.moviecommu.entity.Schedule;
import com.example.moviecommu.repository.HallRepository;
import com.example.moviecommu.repository.ReserveRepository;
import com.example.moviecommu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final HallRepository hallRepository;
    private final UserUtil userUtil;

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

    public List<ReservedSeatDto> reservedSeat(long scheduleId) {
        List<ReservedSeatDto> reserve = reserveRepository.findBySId(scheduleId);
        return reserve;
    }

    public boolean reserve(ReserveDto reserveDto) {
        Long userId = userUtil.getCurrentUsername();
        Reserve reserve = Reserve.builder()
                .amount(reserveDto.getAmount())
                .method(reserveDto.getMethod())
                .pDate(reserveDto.getPDate())
                .userId(userId)
                .seatId(reserveDto.getSeatId())
                .scheduleId(reserveDto.getScheduleId())
                .build();
        //해당 스케쥴 아이디 가서 해당 좌석 검색 결과 있을 시 false 처리 하셈
        if(reserveRepository.findBySeatId(reserve)!=0) //해당 자리
            return false;

        reserveRepository.save(reserve);

        return true;
    }
}
