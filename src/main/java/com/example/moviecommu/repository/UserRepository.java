package com.example.moviecommu.repository;

import com.example.moviecommu.dto.GgimMovieDto;
import com.example.moviecommu.dto.PostDto;
import com.example.moviecommu.dto.PostDtoMyBatis;
import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<PostDtoMyBatis> findByLikePost(Map<String, Object> params);

    int findByGgim(Map<String, Object> params);

    void Ggim(Map<String, Object> params);

    void deleteGgim(Map<String, Object> params);

    List<GgimMovieDto> getGgimMovie(long userId);

    List<Long> findByNickname(String nickname);

    User findByEmail(String email);

    void deleteByEmail(String email);
}
