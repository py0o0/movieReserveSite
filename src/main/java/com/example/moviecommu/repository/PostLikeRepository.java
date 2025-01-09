package com.example.moviecommu.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PostLikeRepository {
    int searchLike(Map<String, Object> map);

    void save(Map<String, Object> map);

    void delete(Map<String, Object> map);
}
