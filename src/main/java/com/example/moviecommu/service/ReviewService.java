package com.example.moviecommu.service;

import com.example.moviecommu.dto.ReviewDto;
import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.Review;

import com.example.moviecommu.repository.MovieRepository;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.ReviewRepository;
import com.example.moviecommu.repository.UserRepository;
import com.example.moviecommu.util.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public void writeReview(String token, String content, float rating, Long movieId) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        Review review = Review.builder()
                .userId(user.getUserId())
                .movieId(movieId)
                .content(content)
                .rating(rating)
                .up(0)
                .down(0)
                .build();
        movieRepository.addRating(movieId, rating);
        reviewRepository.save(review);
    }

    public void deleteReview(String token, Long requestedUserId, Long movieId) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        String role = user.getRole();
        if (!Objects.equals(requestedUserId, user.getUserId()) && !role.equals("ROLE_ADMIN"))
            return;
        reviewRepository.deleteByIds(movieId, requestedUserId);
    }

    public void updateReview(String token, ReviewDto reviewDto, Long movieId) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        Long currentUserId = user.getUserId();
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

    public ResponseEntity<?> findByMovieId(String token, Long movieId) {
        List<Review> reviewList = reviewRepository.findByMovieId(movieId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        List<UserDto> userDtoList = new ArrayList<>();

        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        reviewList.forEach(review -> {
            reviewDtoList.add(getReview(review));
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setEmail(user.getEmail());
            userDtoList.add(userDto);
        });
        return ResponseEntity.ok(Map.of(
                "review", reviewDtoList,
                "user", userDtoList
        ));
    }
}
