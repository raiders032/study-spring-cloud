package com.example.userservice.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class RequestUser {
    @NotNull(message = "email cannot be null")
    @Size(min = 2, message = "email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "name cannot be null")
    @Size(min = 2, message = "name not be less than two characters")
    private String name;

    @NotNull(message = "password cannot be null")
    @Size(min = 8, message = "password must equal or greater than 8 characters")
    private String password;
}
