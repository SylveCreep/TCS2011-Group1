package com.example.server.dto;

import com.example.server.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    private String email;
    private String password;

    public User getUserFromDto(){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
