package com.example.moviecommu.service;

import com.example.moviecommu.dto.MovieDto;
import com.example.moviecommu.entity.Movie;
import com.example.moviecommu.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public MovieDto getMovie(Movie movie) {
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .des(movie.getDes())
                .country(movie.getCountry())
                .director(movie.getDirector())
                .casting(movie.getCasting())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .ageLimit(movie.getAgeLimit())
                .runningTime(movie.getRunningTime())
                .onAir(movie.getOnAir())
                .headCount(movie.getHeadCount())
                .posterUrl(movie.getPosterUrl())
                .build();
    }

    public MovieDto findByMovieId(Long movieId) {
        Movie movie = movieRepository.findByMovieId(movieId);
        return getMovie(movie);
    }

    public MovieDto findByTitle(String title) {
        Movie movie = movieRepository.findByTitle(title);
        return getMovie(movie);
    }

    public List<MovieDto> findByWord(String word) {
        List<Movie> movieList = movieRepository.findByWord(word);
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieList.forEach(movie -> {
            movieDtoList.add(getMovie(movie));
        });

        return movieDtoList;
    }

    public List<MovieDto> getTopTwenties() {
        List<Movie> movieList = movieRepository.findTopTwentyOrderByRatingDesc();
        List<MovieDto> movieDtoList = new ArrayList<>();
        movieList.forEach(movie -> {
            movieDtoList.add(getMovie(movie));
        });

        return movieDtoList;
    }
}
