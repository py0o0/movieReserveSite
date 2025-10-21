package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Following;
import com.example.moviecommu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    Following findByUserAndFollowing(User user, User followingUser);

    void deleteByUserAndFollowing(User user, User followingUser);

    Page<Following> findByUser(User user, Pageable pageable);

    Page<Following> findByFollowing(User user, Pageable pageable);

    long countByUser(User user);

    long countByFollowing(User user);
}
