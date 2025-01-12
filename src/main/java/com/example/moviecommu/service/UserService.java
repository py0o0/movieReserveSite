package com.example.moviecommu.service;

import com.example.moviecommu.dto.*;
import com.example.moviecommu.entity.Following;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.FollowingRepository;
import com.example.moviecommu.repository.UserRepository;
import com.example.moviecommu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean join(UserDto userDto) {
        User user = userRepository.findById(userDto.getId());
        if(user != null)
            return false;

        User nUser = User.builder()
                .id(userDto.getId())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role("ROLE_USER")
                .birth(userDto.getBirth())
                .phone(userDto.getPhone())
                .nickname(userDto.getNickname())
                .build();

        userRepository.saveUser(nUser);
        return true;
    }

    public boolean delete(String username) {
        User user = userRepository.findById(username);
        if(user==null)
            return false;
        if(!Objects.equals(user.getUserId(), userUtil.getCurrentUsername()) && !userUtil.getCurrentUserRole().equals("ROLE_ADMIN"))
            return false;
        userRepository.deleteById(username);
        return true;
    }

    public UserPagingDto getAll(int size, int page) {
        Map<String, Object> params = new HashMap<>();
        page = size * page;
        params.put("size", size);
        params.put("page", page);

        long total = userRepository.userTotal();
        List<User> userList = userRepository.getAll(params);

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setNickname(user.getNickname());
            userDto.setBirth(user.getBirth());
            userDto.setPhone(user.getPhone());
            userDto.setRole(user.getRole());
            userDtoList.add(userDto);
        }

        UserPagingDto userPagingDto = new UserPagingDto();
        userPagingDto.setUsers(userDtoList);
        userPagingDto.setUserCnt(total);

        return userPagingDto;
    }

    public boolean adminJoin(UserDto userDto) {
        User user = userRepository.findById(userDto.getId());
        if(user != null)
            return false;

        User nUser = User.builder()
                .id(userDto.getId())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role("ROLE_ADMIN")
                .birth(userDto.getBirth())
                .phone(userDto.getPhone())
                .nickname(userDto.getNickname())
                .build();

        userRepository.saveUser(nUser);
        return true;
    }

    public boolean follow(String username) {
        User following = userRepository.findById(username);
        Long userId = userUtil.getCurrentUsername();
        if(userId == null || following == null || userId.equals(following.getUserId()))
            return false;

        Following follow = Following.builder()
                .userId(userId)
                .flwingId(following.getUserId())
                .build();
        if(followingRepository.findByFlw(follow) == 0)
            followingRepository.save(follow);
        else  followingRepository.deleteByFlw(follow);
        return true;
    }

    public UserPagingDto getFollowingList(String username, int size, int page) {
        User nUser = userRepository.findById(username);
        Long userId = nUser.getUserId();

        Map<String, Object> params = new HashMap<>();
        page = size * page;

        params.put("size", size);
        params.put("page", page);
        params.put("userId", userId);

        long total = followingRepository.flwingTotal(userId);
        List<User> flwList = followingRepository.findFlwingByUserId(params);

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : flwList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setNickname(user.getNickname());
            userDtoList.add(userDto);
        }

        UserPagingDto userPagingDto = new UserPagingDto();
        userPagingDto.setUsers(userDtoList);
        userPagingDto.setUserCnt(total);

        return userPagingDto;
    }

    public UserPagingDto getFollowerList(String username, int size, int page) {
        User nUser = userRepository.findById(username);
        Long userId = nUser.getUserId();
        Map<String, Object> params = new HashMap<>();
        page = size * page;
        params.put("size", size);
        params.put("page", page);
        params.put("userId", userId);

        long total = followingRepository.flwerTotal(userId);
        List<User> flwList = followingRepository.findFlwerByUserId(params);

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : flwList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setNickname(user.getNickname());
            userDtoList.add(userDto);
        }
        UserPagingDto userPagingDto = new UserPagingDto();
        userPagingDto.setUsers(userDtoList);
        userPagingDto.setUserCnt(total);
        return userPagingDto;
    }

    public boolean flwerDelete(String username) {
        Long userId = userUtil.getCurrentUsername();
        User user = userRepository.findById(username);
        if(userId==null || user==null) return false;

        Following follow = Following.builder()
                .userId(user.getUserId())
                .flwingId(userId)
                .build();
        followingRepository.deleteByFlw(follow);
        return true;
    }

    public UserPageDto getUserPage(String username) {
        User user = userRepository.findById(username);
        Long userId = user.getUserId();

        long following = followingRepository.flwingTotal(userId);
        long follower = followingRepository.flwerTotal(userId);

        return UserPageDto.builder()
                .followers(follower)
                .following(following)
                .build();
    }

    public boolean update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId());
        System.out.println(user);
        if(user == null)
            return false;

        User nUser = User.builder()
                .id(userDto.getId())
                .birth(userDto.getBirth())
                .phone(userDto.getPhone())
                .nickname(userDto.getNickname())
                .build();

        userRepository.update(nUser);
        return true;
    }

    public ResponseEntity<?> likePost(String username,int size, int page) {
        User nUser = userRepository.findById(username);
        Long userId = nUser.getUserId();
        System.out.println(nUser);
        Map<String, Object> params = new HashMap<>();
        page = size * page;
        params.put("size", size);
        params.put("page", page);
        params.put("userId", userId);

        List<PostDtoMyBatis> postDtoList = userRepository.findByLikePost(params);
        System.out.println(postDtoList);
        List<UserDto> userList = new ArrayList<>();
        for(PostDtoMyBatis postDto : postDtoList){
            User user = userRepository.findByUserId(postDto.getUser_id());
            UserDto userDto = new UserDto();
            userDto.setNickname(user.getNickname());
            userDto.setId(user.getId());
            userList.add(userDto);
        }

        return ResponseEntity.ok(Map.of(
                "post", postDtoList,
                "user", userList
        ));
    }

    public ResponseEntity<String> Ggim(int movieId) {
        long userId = userUtil.getCurrentUsername();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("movieId", movieId);
        if(userRepository.findByGgim(params) == 0) {
            userRepository.Ggim(params);
            return ResponseEntity.ok("Ggim");
        }
        return ResponseEntity.badRequest().body("Already Ggim");
    }

    public ResponseEntity<String> ggimdelete(int movieId) {
        long userId = userUtil.getCurrentUsername();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("movieId", movieId);
        if(userRepository.findByGgim(params) == 1) {
            userRepository.deleteGgim(params);
            return ResponseEntity.ok("Ggim delete");
        }
        return ResponseEntity.badRequest().body("Not Ggim");
    }

    public ResponseEntity<?> ggimMovie() {
        long userId = userUtil.getCurrentUsername();
        System.out.println(userId);
        List<GgimMovieDto> ggimMovieList =userRepository.getGgimMovie(userId);

        return ResponseEntity.ok(Map.of(
                "movie", ggimMovieList
        ));
    }

    public UserDto getUserInfo() {
        long userId = userUtil.getCurrentUsername();

        User user = userRepository.findByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setNickname(user.getNickname());
        userDto.setId(user.getId());
        userDto.setBirth(user.getBirth());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole());
        return userDto;
    }

}
