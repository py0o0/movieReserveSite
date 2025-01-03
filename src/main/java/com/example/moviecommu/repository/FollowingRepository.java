package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Following;
import com.example.moviecommu.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FollowingRepository {
    void save(Following follow);

    int findByFlw(Following follow);

    void deleteByFlw(Following follow);

    long flwTotal(String userId);

    List<User> findByUserId(Map<String, Object> params);
}
