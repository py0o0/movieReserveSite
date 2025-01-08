package com.example.moviecommu.service;

import com.example.moviecommu.dto.ReviewDto;
import com.example.moviecommu.entity.Review;
import com.example.moviecommu.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

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
