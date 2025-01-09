package com.example.moviecommu.service;

import com.example.moviecommu.dto.ReviewDto;
import com.example.moviecommu.entity.Review;
import com.example.moviecommu.repository.MovieRepository;
import com.example.moviecommu.repository.ReviewRepository;
import com.example.moviecommu.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    private MovieRepository movieRepository;
    private UserUtil userUtil;

    public void writeReview(ReviewDto reviewDto, Long movieId) {
        Long currentUserId = userUtil.getCurrentUsername();
        if (!Objects.equals(reviewDto.getUserId(), currentUserId)) {
            return;
        }
        Review review = Review.builder()
                .userId(currentUserId)
                .movieId(reviewDto.getMovieId())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .up(reviewDto.getUp())
                .down(reviewDto.getDown())
                .build();
        reviewRepository.save(review);
        movieRepository.ratingAdd(movieId, reviewDto.getRating());
    }

    public void deleteReview(Long requestedUserId, Long movieId) {
        Long currentUserId = userUtil.getCurrentUsername();
        if (!Objects.equals(requestedUserId, currentUserId)) {
            return;
        }
        reviewRepository.deleteByIds(movieId, currentUserId);
    }

    public void updateReview(ReviewDto reviewDto, Long movieId) {
        Long currentUserId = userUtil.getCurrentUsername();
        if (!Objects.equals(reviewDto.getUserId(), currentUserId)) {
            return;
        }
        Review review = Review.builder()
                .userId(currentUserId)
                .movieId(reviewDto.getMovieId())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .up(reviewDto.getUp())
                .down(reviewDto.getDown())
                .build();
        reviewRepository.save(review);
    }

    public ReviewDto getReview(Review review) {
        return ReviewDto.builder()
                .movieId(review.getMovieId())
                .userId(review.getUserId())
                .content(review.getContent())
                .rating(review.getRating())
                .up(review.getUp())
                .down(review.getDown())
                .build();
    }

    public List<ReviewDto> findByMovieId(Long movieId) {
        List<Review> reviewList = reviewRepository.findByMovieId(movieId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        reviewList.forEach(review -> {
            reviewDtoList.add(getReview(review));
        });
        return reviewDtoList;
    }
}
