package com.example.moviecommu.controller;

import com.example.moviecommu.dto.MovieDto;
import com.example.moviecommu.dto.ReviewDto;
import com.example.moviecommu.entity.Movie;
import com.example.moviecommu.entity.Review;
import com.example.moviecommu.service.MovieService;
import com.example.moviecommu.service.ReviewService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MovieController {
    private final MovieService movieService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    @GetMapping("/movie")
    public List<MovieDto> showMovieRank() {
        return movieService.getTopTwenties();
    }

    @GetMapping(value = "/movie/{movieId}", produces = "application/json; charset=UTF-8") //유저네임도 같이 반환
    public ResponseEntity<?> showMovieDetail(@PathVariable("movieId") Long movieId){
        MovieDto movieDto = movieService.findByMovieId(movieId);
        ResponseEntity<?> reviewData = reviewService.findByMovieId(movieId);

        return ResponseEntity.ok(Map.of(
                "movie", movieDto,
                "reviwe", reviewData
        ));
    }
}
