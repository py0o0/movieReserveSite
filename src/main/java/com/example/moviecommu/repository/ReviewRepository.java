package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.movieId = :movieId")
    List<Review> findByMovieId(@Param("movieId") Long movieId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Review r WHERE r.movieId = :movieId AND r.userId = :userId")
    void deleteByIds(@Param("movieId") Long movieId, @Param("userId") Long userId);
}
