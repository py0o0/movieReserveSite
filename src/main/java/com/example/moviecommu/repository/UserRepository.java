package com.example.moviecommu.repository;

import com.example.moviecommu.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    User findByUsername(String username);
}
