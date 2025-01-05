package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Following;
import com.example.moviecommu.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FollowingRepository {
    void save(Following follow); //팔로우 정보 저장

    int findByFlw(Following follow); //팔로잉 정보 확인

    void deleteByFlw(Following follow); // 팔로잉 삭제

    long flwingTotal(Long userId); //유저가 팔로잉 한 총 수

    List<User> findByUserId(Map<String, Object> params); //유저의 팔로잉 정보 추출(페이징)
}
