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
    public List<MovieDto> search(@PathVariable("word") String word) {
        return movieService.findByWord(word);
    }
}
