package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r from Review r WHERE r.movieId = :movieId")
    List<Review> findByMovieId(@Param("movieId") Long movieId);
}
