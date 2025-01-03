package com.example.moviecommu.repository;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRepository {
    User findByUsername(String username);

    void saveUser(User user);

    int exsistByUsername(String userName);

    void deleteByUsername(String username);

    List<User> getAll(Map<String, Object> params);

    long userTotal();
}
