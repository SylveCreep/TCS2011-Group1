package com.example.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.server.constant.Constant;
import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.UserListResponse;
import com.example.server.service.UserService;
import com.example.server.util.ResponseUtils;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateAccount user){
        try {
            User createdUser = userService.saveRegister(user);
            if(createdUser == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Create user failed", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS,"Create user success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Create user failed", HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('ADMIN')")
    // @GetMapping
    // public ResponseEntity<?> showUser(@RequestBody PagingRequest pagingRequest){
    //     try {
    //         if(pagingRequest.getLimit() < 0 || pagingRequest.getPage() < 0){
    //             return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
    //         }
    //         List<UserListResponse> users = userService.getUserListResponse(pagingRequest);
    //         if(users == null){
    //             return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Show failed", HttpStatus.BAD_REQUEST);
    //         }

    //         return responseUtils.getResponseEntity(users, Constant.SUCCESS,"Show success",users.size(), HttpStatus.OK);
    //     } catch (Exception e) {
    //         return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Show failed", HttpStatus.BAD_REQUEST);
    //     }
    // }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value="/filter")
    public ResponseEntity<?> showUserBySearch(@RequestBody UserSearchRequest userSearchRequest){
        try {
            if(userSearchRequest.getLimit() < 0 || userSearchRequest.getPage() < 0){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            List<UserListResponse> users = userService.searchUserByRoleAndFacul(userSearchRequest);
            if(users == null){
                return responseUtils.getResponseEntity(users, Constant.SUCCESS,"Don't have user", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(users, Constant.SUCCESS,"Show user success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Show user failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/{id}",consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name="id") Integer id){
        try {
            if(id == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Must has user id", HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = userService.deleteUser(id);
            if(is_deleted == false){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Delete user fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS,"Delete user successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Delete user fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> update(@RequestBody UserDto userDto){
        try {
            if(userDto.getId() == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE ,"Must has user id", HttpStatus.BAD_REQUEST);
            }
            UserDto user = userService.update(userDto);
            if(user == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Update user fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(user, Constant.SUCCESS,"Update user successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Update user fail", HttpStatus.BAD_REQUEST);
        }
    }


}
