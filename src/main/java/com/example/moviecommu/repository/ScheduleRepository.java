package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleRepository {
    List<Schedule> findByMovieId(int movieId);

    int findBySId(long scheduleId);

    int findByScheduleId(long scheduleId);
}
