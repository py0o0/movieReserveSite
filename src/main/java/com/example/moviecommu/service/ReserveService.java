package com.example.moviecommu.service;

import com.example.moviecommu.dto.MyReserveDto;
import com.example.moviecommu.dto.ReserveDto;
import com.example.moviecommu.dto.ReservedSeatDto;
import com.example.moviecommu.dto.ScheduleHallDto;
import com.example.moviecommu.entity.*;
import com.example.moviecommu.repository.*;
import com.example.moviecommu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final HallRepository hallRepository;
    private final JwtUtil jwtUtil;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public List<ScheduleHallDto> schedule(int movieId) {

        //영화 Id 확인해서 예매가능한지 로직 추가 널 반환하게
        List<ScheduleHallDto> scheduleHallDtoList = new ArrayList<>();
        List<Schedule> scheduleInfo = scheduleRepository.findByMovieId(movieId);

        for(Schedule schedule : scheduleInfo) {
            Hall hall = hallRepository.findByHallId(schedule.getHallId());
            ScheduleHallDto scheduleHallDto = ScheduleHallDto.builder()
                    .date(schedule.getDate())
                    .day(schedule.getDay())
                    .hallId(schedule.getHallId())
                    .movieId(schedule.getMovieId())
                    .name(hall.getName())
                    .price(hall.getPrice())
                    .scheduleId(schedule.getScheduleId())
                    .startTime(schedule.getStartTime())
                    .build();
            scheduleHallDtoList.add(scheduleHallDto);
        }
        return scheduleHallDtoList;
    }

    public List<ReservedSeatDto> reservedSeat(long scheduleId) {
        List<ReservedSeatDto> reserve = reserveRepository.findBySId(scheduleId);
        return reserve;
    }

    public boolean reserve(String token, ReserveDto reserveDto) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        Reserve reserve = Reserve.builder()
                .amount(reserveDto.getAmount())
                .method(reserveDto.getMethod())
                .userId(user.getUserId())
                .seatId(reserveDto.getSeatId())
                .scheduleId(reserveDto.getScheduleId())
                .build();

        String birth = user.getBirth();

        long movieId = scheduleRepository.findByScheduleId(reserve.getScheduleId());
        Movie movie = movieRepository.findByMovieId(movieId);
        int ageLimit = movie.getAgeLimit();

        LocalDate birthDate = LocalDate.parse(birth);
        int age = Period.between(birthDate, LocalDate.now()).getYears();


        if(reserveRepository.findBySeatId(reserve)!=0 || scheduleRepository.findBySId(reserve.getScheduleId()) == 0 || age < ageLimit)
            return false;

        reserveRepository.save(reserve);

        return true;
    }

    public List<MyReserveDto> getMyReserve(String token) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        return reserveRepository.findByMyReserveLast(user.getUserId());
    }

    public List<MyReserveDto> getMyReservePrevios(String token) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        return reserveRepository.findByMyReservePrevious(user.getUserId());
    }

    public boolean reserveDelete(String token, String seatId, long scheduleId) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        Reserve reserve = Reserve.builder()
                .seatId(seatId)
                .scheduleId(scheduleId)
                .userId(user.getUserId())
                .build();

        if(reserveRepository.isReserved(reserve) == 0)
            return false;
        reserveRepository.reserveDelete(reserve);
        return true;

    }
}
