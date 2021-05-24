package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserDto {
    private String email;
    private String name;
    private String password;
    private String userId;
    private Date createdAt;
    private String encryptedPassword;
    private List<ResponseOrder> orders;
}
