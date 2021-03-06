package com.example.userservice.controller;

import com.example.userservice.User;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final Environment environment;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service port(local.server.port)= %s \n" +
                             " port(server.port)= %s \n" +
                             "token secret= %s, \n" +
                             "token expiration time= %s"
                , environment.getProperty("local.server.port")
                , environment.getProperty("server.port")
                , environment.getProperty("token.secret")
                , environment.getProperty("token.expiration_time")
        );
    }

    @GetMapping("/welcome")
    public String welcome() {
        return environment.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser requestUser) {
        UserDto userDto = modelMapper.map(requestUser, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        ResponseUser responseUser = modelMapper.map(createdUser, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<User> users = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();

        users.forEach(user-> result.add(modelMapper.map(user, ResponseUser.class)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable String userId){
        UserDto userDto = userService.getUserByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(userDto, ResponseUser.class));
    }
}
