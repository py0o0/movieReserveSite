package com.example.moviecommu.repository;

import com.example.moviecommu.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByMovieId(Long movieId);

    Movie findByTitle(String title);

    @Query("SELECT m FROM Movie m")
    List<Movie> getAllMovies();

    @Query("SELECT m FROM Movie m WHERE m.title LIKE CONCAT('%',:word,'%') OR m.director LIKE CONCAT('%',:word,'%') OR m.casting LIKE CONCAT('%',:word,'%')")
    List<Movie> findByWord(@Param("word")String word);

    @Query("SELECT m FROM Movie m ORDER BY m.rating DESC LIMIT 20")
    List<Movie> findTopTwentyOrderByRatingDesc();

    @Modifying
    @Query("UPDATE Movie m SET m.headCount = m.headCount + 1, m.rating = ((m.rating * 10000) + :score) / (m.headCount + 1) WHERE m.movieId = :movieId")
    @Transactional
    void addRating(@Param("movieId")Long movieId, @Param("score")float score);
}
