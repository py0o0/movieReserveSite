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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = "/movie/{id}", produces = "application/json; charset=UTF-8")
    public String showMovieDetail(@PathVariable("id") Long id) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("movie", new JSONObject(movieService.findByMovieId(id)));
        json.put("reviews", new JSONArray(reviewService.findByMovieId(id)));

        return json.toString();
    }
}
