package com.example.server.service;

import java.util.List;

import com.example.server.dto.UserDto;
import com.example.server.entity.User;

public interface UserService {
    User save(UserDto user);

    List<User> findAll();
    
    User findOne(String email);
}
