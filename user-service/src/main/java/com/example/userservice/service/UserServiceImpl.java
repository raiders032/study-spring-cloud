package com.example.userservice.service;

import com.example.userservice.User;
import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = modelMapper.map(userDto, User.class);
        user.setEncryptedPassword("encrypted_password");
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }
}
