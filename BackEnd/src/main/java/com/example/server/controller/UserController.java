package com.example.server.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.server.constant.Constant;
import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.UserLastPageResponse;
import com.example.server.model.response.UserListResponse;
import com.example.server.model.response.UserResponse;
import com.example.server.service.BanService;
import com.example.server.service.UserService;
import com.example.server.util.ResponseUtils;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @Autowired
    private BanService banService;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @PreAuthorize("hasRole('ADMIN')")
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
            UserLastPageResponse users = userService.searchUserByRoleAndFacul(userSearchRequest);
            if(users == null){
                return responseUtils.getResponseEntity(users, Constant.SUCCESS,"Don't have user", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(users.getList(), Constant.SUCCESS,"Show user success",users.getLastPage(), HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Show user failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/{id}",consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name="id") Long id){
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
    @PatchMapping(consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> update(@RequestBody CreateAccount userDto){
        try {
            if(userDto.getId() == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE ,"Must has user id", HttpStatus.BAD_REQUEST);
            }
            Boolean user = userService.update(userDto);
            if(user == false){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Update user fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(user, Constant.SUCCESS,"Update user successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Update user fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value="/{id}",consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> getUser(@PathVariable(name="id") Long id){
        try {
            if(id == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Must has user id", HttpStatus.BAD_REQUEST);
            }
            UserResponse user = userService.findById(id);
            if(user == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Get user fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(user, Constant.SUCCESS,"Get user successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Get user fail", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token){
        try {
            String result = banService.add(token.replace(TOKEN_PREFIX,""));
            if(result == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Log out failed", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, result, HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Log out failed", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/nrm",consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> getUserNormal(){
        try {
            List<UserResponse> users = userService.getUserNotIsManager();
            if(users == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Get user fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(users, Constant.SUCCESS,"Get user successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Get user fail", HttpStatus.BAD_REQUEST);
        }
    }


}
