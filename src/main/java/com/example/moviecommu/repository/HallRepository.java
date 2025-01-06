package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Hall;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HallRepository {
    Hall findByHallId(long hallId);
}
