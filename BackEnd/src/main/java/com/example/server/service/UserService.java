package com.example.server.service;

import java.util.List;

import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.UserLastPageResponse;
import com.example.server.model.response.UserListResponse;
import com.example.server.model.response.UserResponse;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;


public interface UserService {
    User saveGuestRegister(UserDto user);

    User saveRegister(CreateAccount user);

    Boolean deleteUser(Long id);

    List<User> findAll();
    
    User findOne(String email);

    UserResponse findById(Long id);

    UserDto update(UserDto user);

    List<Object> getUserListResponse(PagingRequest pagingRequest);

    UserLastPageResponse searchUserByRoleAndFacul(UserSearchRequest userSearchRequest);

    List<UserResponse> getUserNotIsManager();

}
