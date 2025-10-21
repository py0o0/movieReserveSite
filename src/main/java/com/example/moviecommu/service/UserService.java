package com.example.moviecommu.service;

import com.example.moviecommu.dto.*;
import com.example.moviecommu.entity.Following;
import com.example.moviecommu.entity.PostHeart;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.FollowingRepository;
import com.example.moviecommu.repository.PostHeartRepository;
import com.example.moviecommu.repository.UserRepository;
import com.example.moviecommu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PostHeartRepository postHeartRepository;

    public ResponseEntity<?> join(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        if(user != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        user = User.builder()
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role("ROLE_USER")
                .birth(userDto.getBirth())
                .phone(userDto.getPhone())
                .nickname(userDto.getNickname())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(String token) {
        String email = jwtUtil.getEmail(token);
        userRepository.deleteByEmail(email);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getAll(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userList = userRepository.findAll(pageable);
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = UserDto.builder()
                    .email(user.getEmail())
                    .role(user.getRole())
                    .birth(user.getBirth())
                    .phone(user.getPhone())
                    .nickname(user.getNickname())
                    .userId(user.getUserId())
                    .build();
            userDtoList.add(userDto);
        }
        return ResponseEntity.ok(userDtoList);
    }

    public ResponseEntity<?> adminJoin(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        user = User.builder()
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role("ROLE_ADMIN")
                .birth(userDto.getBirth())
                .phone(userDto.getPhone())
                .nickname(userDto.getNickname())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> follow(String token, String email) {
        String curUserEmail = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(curUserEmail);

        User followingUser = userRepository.findByEmail(email);

        Following following = followingRepository.findByUserAndFollowing(user, followingUser);
        if(following == null) {
            following = Following.builder()
                    .user(user)
                    .following(followingUser)
                    .build();
            followingRepository.save(following);
        }
        else{
            followingRepository.delete(following);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getFollowingList(String token, int size, int page) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        Pageable pageable = PageRequest.of(page, size);

        Page<Following> followingList = followingRepository.findByUser(user, pageable);

        List<UserDto> userDtoList = new ArrayList<>();
        for (Following following : followingList) {
            UserDto userDto = UserDto.builder()
                    .userId(following.getUser().getUserId())
                    .email(following.getUser().getEmail())
                    .build();
            userDtoList.add(userDto);
        }

        return ResponseEntity.ok().body(userDtoList);
    }

    public ResponseEntity<?> getFollowerList(String token, int size, int page) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        Pageable pageable = PageRequest.of(page, size);

        Page<Following> followingList = followingRepository.findByFollowing(user, pageable);

        List<UserDto> userDtoList = new ArrayList<>();
        for (Following following : followingList) {
            UserDto userDto = UserDto.builder()
                    .userId(following.getUser().getUserId())
                    .email(following.getUser().getEmail())
                    .build();
            userDtoList.add(userDto);
        }

        return ResponseEntity.ok().body(userDtoList);
    }

    public ResponseEntity<?> flwerDelete(String token, String email) {
        String curUserEmail = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(curUserEmail);

        User followingUser = userRepository.findByEmail(email);

        followingRepository.deleteByUserAndFollowing(user, followingUser);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getUserPage(String email) {
        User user = userRepository.findByEmail(email);

        long following = followingRepository.countByUser(user);
        long follower = followingRepository.countByFollowing(user);

        UserPageDto userPageDto = UserPageDto.builder()
                .followers(follower)
                .following(following)
                .build();
        UserDto userDto = UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .birth(user.getBirth())
                .build();
        return ResponseEntity.ok(Map.of(
                "user", userDto,
                "userPage", userPageDto
        ));
    }

    public ResponseEntity<?> update(String token, UserDto userDto) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);

        user.setNickname(userDto.getNickname());
        user.setBirth(userDto.getBirth());
        user.setPhone(userDto.getPhone());

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> likePost(String email,int size, int page) {
        User user = userRepository.findByEmail(email);

        Pageable pageable = PageRequest.of(page, size);

        Page<PostHeart> postHeartList = postHeartRepository.findByUser(user, pageable);

        List<PostDto> postDtoList = new ArrayList<>();
        for (PostHeart postHeart: postHeartList) {
            PostDto postDto = PostDto.builder()
                    .postId(postHeart.getPost().getPostId())
                    .title(postHeart.getPost().getTitle())
                    .view(postHeart.getPost().getView())
                    .email(postHeart.getPost().getUser().getEmail())
                    .heart(postHeart.getPost().getHeart())
                    .build();
            postDtoList.add(postDto);
        }

        return ResponseEntity.ok().body(postDtoList);
    }

    public ResponseEntity<String> Ggim(String token, int movieId) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        long userId = user.getUserId();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("movieId", movieId);
        if(userRepository.findByGgim(params) == 0) {
            userRepository.Ggim(params);
            return ResponseEntity.ok("Ggim");
        }
        return ResponseEntity.badRequest().body("Already Ggim");
    }

    public ResponseEntity<String> ggimdelete(String token, int movieId) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        long userId = user.getUserId();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("movieId", movieId);
        if(userRepository.findByGgim(params) == 1) {
            userRepository.deleteGgim(params);
            return ResponseEntity.ok("Ggim delete");
        }
        return ResponseEntity.badRequest().body("Not Ggim");
    }

    public ResponseEntity<?> ggimMovie(String token) {
        String email = jwtUtil.getEmail(token);
        User user = userRepository.findByEmail(email);
        long userId = user.getUserId();
        System.out.println(userId);
        List<GgimMovieDto> ggimMovieList =userRepository.getGgimMovie(userId);

        return ResponseEntity.ok(Map.of(
                "movie", ggimMovieList
        ));
    }

}
