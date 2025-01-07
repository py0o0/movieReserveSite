package com.example.moviecommu.repository;
import com.example.moviecommu.dto.MyReserveDto;
import com.example.moviecommu.dto.ReservedSeatDto;
import com.example.moviecommu.entity.Reserve;
import com.example.moviecommu.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReserveRepository {
    List<Schedule> findByMovieId(int movieId);

    List<ReservedSeatDto> findBySId(long scheduleId);

    void save(Reserve reserve);

    int findBySeatId(Reserve reserve);

    List<MyReserveDto> findByMyReserveLast(Long userId);

    List<MyReserveDto> findByMyReservePrevious(Long userId);

    int isReserved(Reserve reserve);

    void reserveDelete(Reserve reserve);
}
