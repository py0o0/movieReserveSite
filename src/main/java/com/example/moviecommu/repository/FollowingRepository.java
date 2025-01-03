package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Following;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowingRepository {
    void save(Following follow);

    int findByFlw(Following follow);

    void deleteByFlw(Following follow);
}
