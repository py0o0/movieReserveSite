package com.example.moviecommu.service;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

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
}
