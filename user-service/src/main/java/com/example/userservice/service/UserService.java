package com.example.userservice.service;

import com.example.userservice.User;
import com.example.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<User> getUserByAll();

    UserDto getUserDetailsByEmail(String email);
}
