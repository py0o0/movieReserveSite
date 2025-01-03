package com.example.moviecommu.service;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.dto.UserPageResponseDto;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.UserRepository;
import com.example.moviecommu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean join(UserDto userDto) {
        if(userRepository.exsistByUsername(userDto.getUserName()) == 1)
            return false;

        User user = User.builder()
                .userName(userDto.getUserName())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.saveUser(user);
        return true;
    }

    public boolean delete(String username) {
        System.out.println(userUtil.getCurrentUserRole());
        if(!username.equals(userUtil.getCurrentUsername()) && !userUtil.getCurrentUserRole().equals("ROLE_ADMIN"))
            return false;
        if( userRepository.exsistByUsername(username) == 0)
            return false;
        userRepository.deleteByUsername(username);
        return true;
    }

    public UserPageResponseDto getAll(int size, int page) {
        Map<String, Object> params = new HashMap<>();
        page = size * page;
        params.put("size", size);
        params.put("page", page);

        long total = userRepository.userTotal();
        List<User> userList = userRepository.getAll(params);

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setUserName(user.getUsername());
            userDtoList.add(userDto);
        }

        UserPageResponseDto userPageResponseDto = new UserPageResponseDto();
        userPageResponseDto.setUsers(userDtoList);
        userPageResponseDto.setUserCnt(total);

        return userPageResponseDto;
    }
}
