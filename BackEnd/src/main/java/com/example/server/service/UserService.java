package com.example.server.service;

import java.util.List;

import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.UserListResponse;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;


public interface UserService {
    User saveGuestRegister(UserDto user);

    User saveRegister(CreateAccount user);

    Boolean deleteUser(int id);

    List<User> findAll();
    
    User findOne(String email);

    UserDto update(UserDto user);

    List<UserListResponse> getUserListResponse(PagingRequest pagingRequest);

    List<UserListResponse> searchUserByRoleAndFacul(UserSearchRequest userSearchRequest);

}