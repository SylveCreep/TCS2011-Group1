package com.example.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.UserListResponse;
import com.example.server.service.UserService;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.server.constant.Constant.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    
    @Autowired
    private UserService userService;

    @Autowired
    private ResponseUtils responseUtils;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateAccount user){
        try {
            userService.saveRegister(user);
            return responseUtils.getResponseEntity("NULL",SUCCESS,"Register success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL",FAILURE,"Register failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> showUser(@RequestParam PagingRequest pagingRequest){
        try {
            if(pagingRequest.getLimit() >= 0 || pagingRequest.getPage() > 0){
                return responseUtils.getResponseEntity("NULL",FAILURE,"Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            List<UserListResponse> users = userService.getUserListResponse(pagingRequest);
            return responseUtils.getResponseEntity(users,SUCCESS,"Register success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL",FAILURE,"Register failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value="/filter", consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> showUserBySearch(@RequestBody UserSearchRequest userSearchRequest){
        try {
            if(userSearchRequest.getLimit() >= 0 || userSearchRequest.getPage() > 0){
                return responseUtils.getResponseEntity("NULL",FAILURE,"Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            List<UserListResponse> users = userService.searchUserByRoleAndFacul(userSearchRequest);
            return responseUtils.getResponseEntity(users,SUCCESS,"Register success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL",FAILURE,"Register failed", HttpStatus.BAD_REQUEST);
        }
    }


}
