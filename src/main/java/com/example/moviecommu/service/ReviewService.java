package com.example.moviecommu.service;

import com.example.moviecommu.dto.ReviewDto;
import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.Review;
import com.example.moviecommu.repository.MovieRepository;
import com.example.moviecommu.entity.User;

import com.example.moviecommu.repository.ReviewRepository;
import com.example.moviecommu.repository.UserRepository;
import com.example.moviecommu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


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
        movieRepository.addRating(movieId, reviewDto.getRating());
        reviewRepository.save(review);
    }

    public void deleteReview(Long requestedUserId, Long movieId) {
//        Long currentUserId = userUtil.getCurrentUsername();
//        if (!Objects.equals(requestedUserId, currentUserId)) {
//            return;
//        }
        reviewRepository.deleteByIds(movieId, requestedUserId);
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

    public ResponseEntity<?> findByMovieId(Long movieId) {
        List<Review> reviewList = reviewRepository.findByMovieId(movieId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        List<UserDto> userDtoList = new ArrayList<>();

        reviewList.forEach(review -> {
            reviewDtoList.add(getReview(review));
            User user = userRepository.findByUserId(review.getUserId());
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setId(user.getId());
            userDtoList.add(userDto);
        });
        return ResponseEntity.ok(Map.of(
                "review", reviewDtoList,
                "user", userDtoList
        ));
    }
}
