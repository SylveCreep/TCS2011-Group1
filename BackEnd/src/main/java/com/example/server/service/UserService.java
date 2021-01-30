package com.example.server.service;

import java.util.List;

import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.*;


public interface UserService {
    User saveGuestRegister(UserDto user);

    User saveRegister(CreateAccount user);

    List<User> findAll();
    
    User findOne(String email);

}
