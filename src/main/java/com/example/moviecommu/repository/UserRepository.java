package com.example.moviecommu.repository;

import com.example.moviecommu.dto.GgimMovieDto;
import com.example.moviecommu.dto.PostDto;
import com.example.moviecommu.dto.PostDtoMyBatis;
import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRepository {
    User findById(String username); //id로 유저 검색

    void saveUser(User user); //회원가입

    void deleteById(String username); // 유저 삭제

    List<User> getAll(Map<String, Object> params); //유저 목록 추출(페이징)

    long userTotal(); //유저의 총 수

    void update(User nUser);

    User findByUserId(long userId);

    List<PostDtoMyBatis> findByLikePost(Map<String, Object> params);

    int findByGgim(Map<String, Object> params);

    void Ggim(Map<String, Object> params);

    void deleteGgim(Map<String, Object> params);

    List<GgimMovieDto> getGgimMovie(long userId);

    List<Long> findByNickname(String nickname);
}
