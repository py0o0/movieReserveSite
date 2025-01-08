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
    public List<Movie> showMovieRank() {
        List<MovieDto> movieDtoList = movieService.getTopTwenties();
        List<Movie> movieList = new ArrayList<>();

        movieDtoList.forEach(movieDto -> {
            Movie movie = new Movie();
            movie.setMovieId(movieDto.getMovieId());
            movie.setTitle(movieDto.getTitle());
            movie.setDes(movieDto.getDes());
            movie.setCountry(movieDto.getCountry());
            movie.setDirector(movieDto.getDirector());
            movie.setCasting(movieDto.getCasting());
            movie.setGenre(movieDto.getGenre());
            movie.setRating(movieDto.getRating());
            movie.setReleaseDate(movieDto.getReleaseDate());
            movie.setAgeLimit(movieDto.getAgeLimit());
            movie.setRunningTime(movieDto.getRunningTime());
            movie.setOnAir(movieDto.getOnAir());
            movie.setHeadCount(movieDto.getHeadCount());
            movie.setPosterUrl(movieDto.getPosterUrl());
            movieList.add(movie);
        });

        return movieList;
    }

    @GetMapping("/movie/{id}")
    public String showMovieDetail(@PathVariable("id") Long id) throws JSONException {
        Movie movie = new Movie();
        MovieDto movieDto = movieService.findByMovieId(id);
        List<ReviewDto> reviewDtoList = reviewService.findByMovieId(id);
        List<Review> reviewList = new ArrayList<>();

        movie.setMovieId(movieDto.getMovieId());
        movie.setTitle(movieDto.getTitle());
        movie.setDes(movieDto.getDes());
        movie.setCountry(movieDto.getCountry());
        movie.setDirector(movieDto.getDirector());
        movie.setCasting(movieDto.getCasting());
        movie.setGenre(movieDto.getGenre());
        movie.setRating(movieDto.getRating());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setAgeLimit(movieDto.getAgeLimit());
        movie.setRunningTime(movieDto.getRunningTime());
        movie.setOnAir(movieDto.getOnAir());
        movie.setHeadCount(movieDto.getHeadCount());
        movie.setPosterUrl(movieDto.getPosterUrl());

        JSONObject json = new JSONObject();

        JSONObject movieObj = new JSONObject(movie);
        JSONArray reviewArray = new JSONArray(reviewDtoList);
        json.put("movie", movieObj);
        json.put("reviews", reviewArray);

        return json.toString();
    }
}
