package com.example.moviecommu.repository;
import com.example.moviecommu.dto.ReservedSeatDto;
import com.example.moviecommu.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReserveRepository {
    List<Schedule> findByMovieId(int movieId);

    List<ReservedSeatDto> findBySId(long scheduleId);
}
