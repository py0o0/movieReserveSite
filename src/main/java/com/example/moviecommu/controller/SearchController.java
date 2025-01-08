package com.example.moviecommu.controller;

import com.example.moviecommu.dto.MovieDto;
import com.example.moviecommu.entity.Movie;
import com.example.moviecommu.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController {
    private final MovieService movieService;

    public SearchController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search/{word}")
    public List<Movie> search(@PathVariable("word") String word) {
        List<MovieDto> movieDtoList = movieService.findByWord(word);
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
}
