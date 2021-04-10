package com.example.server.service;

import java.util.List;

import com.example.server.dto.FacebookUserDto;
import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.TotalCountResponse;
import com.example.server.model.response.UserLastPageResponse;
import com.example.server.model.response.UserListResponse;
import com.example.server.model.response.UserResponse;
import com.example.server.model.response.UserWithMostContributionResponse;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User saveGuestRegister(CreateAccount user);

    User saveRegister(CreateAccount user, MultipartFile file);

    Boolean deleteUser(Long id);

    List<User> findAll();

    User findOne(String email);

    UserResponse findById(Long id);

    Boolean update(CreateAccount user, MultipartFile file);

    Boolean updatePassword(CreateAccount form, User user);

    List<Object> getUserListResponse(PagingRequest pagingRequest);

    UserLastPageResponse searchUserByRoleAndFacul(UserSearchRequest userSearchRequest);

    List<UserResponse> getUserNotIsManager();

    Boolean validateResetPasswordKey(String key);

    FacebookUserDto getFacebookUser(String accessToken);

    LoginUser getLoginJwtTokenByFacebook(String accessToken);

    Boolean changePassword(ChangePasswordRequest request);

    TotalCountResponse getTotalCountResponse();

    List<UserWithMostContributionResponse> getUserWithMostContribution();

}
