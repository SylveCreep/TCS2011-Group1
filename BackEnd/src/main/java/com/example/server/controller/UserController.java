package com.example.server.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.server.constant.Constant;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.UserLastPageResponse;
import com.example.server.model.response.UserResponse;
import com.example.server.service.BanService;
import com.example.server.service.FileService;
import com.example.server.service.UserService;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private FileService fileService;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @PreAuthorize("hasRole('R0001')")
    @PostMapping
    public ResponseEntity<?> createUser(CreateAccount user, @RequestPart("file") MultipartFile file,
            HttpServletRequest httpServletRequest) {
        try {
            HashMap<String, Object> validateResult = responseUtils.validateCreateAccountRequest(user, file, 0);
            Object validateRes = validateResult.get("result");
            if (Integer.parseInt(validateRes.toString()) == -1) {
                return responseUtils.getActionResponseEntity("NULL", Constant.FAILURE, "Create user failed",
                        validateResult, HttpStatus.BAD_REQUEST);
            }
            if (file == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Create user failed, missing avatar",
                        HttpStatus.BAD_REQUEST);
            }
            User createdUser = userService.saveRegister(user, file);
            if (createdUser == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Create user failed",
                        HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Create user success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Create user failed",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0001') or hasRole('R0002') or hasRole('R0003')")
    @PostMapping(value = "/filter")
    public ResponseEntity<?> showUserBySearch(@RequestBody UserSearchRequest userSearchRequest) {
        try {
            if (userSearchRequest.getLimit() < 0 || userSearchRequest.getPage() < 0) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,
                        "Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            UserLastPageResponse users = userService.searchUserByRoleAndFacul(userSearchRequest);
            if (users == null) {
                return responseUtils.getResponseEntity(users, Constant.SUCCESS, "Don't have user", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(users.getList(), Constant.SUCCESS, "Show user success",
                    users.getLastPage(), HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Show user failed",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0001')")
    @DeleteMapping(value = "/{id}", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has user id",
                        HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = userService.deleteUser(id);
            if (is_deleted == false) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Delete user fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Delete user successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Delete user fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0001')")
    @PatchMapping(produces = "application/json")
    public ResponseEntity<?> update(CreateAccount userDto, @RequestParam("file") MultipartFile file,
            HttpServletRequest httpServletRequest) {
        try {
            HashMap<String, Object> validateResult = responseUtils.validateCreateAccountRequest(userDto, file, 1);
            Object validateRes = validateResult.get("result");
            if (Integer.parseInt(validateRes.toString()) == -1) {
                return responseUtils.getActionResponseEntity("NULL", Constant.FAILURE, "Update user failed",
                        validateResult, HttpStatus.BAD_REQUEST);
            }
            if (userDto.getId() == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has user id",
                        HttpStatus.BAD_REQUEST);
            }
            Boolean user = userService.update(userDto, file);
            if (user == false) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update user fail",
                        HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(user, Constant.SUCCESS, "Update user successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update user fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('R0001') or hasRole('R0002') or hasRole('R0003')")
    @GetMapping(value = "/{id}", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has user id",
                        HttpStatus.BAD_REQUEST);
            }
            UserResponse user = userService.findById(id);
            if (user == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get user fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(user, Constant.SUCCESS, "Get user successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get user fail", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        try {
            String result = banService.add(token.replace(TOKEN_PREFIX, ""));
            if (result == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Log out failed",
                        HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, result, HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Log out failed", HttpStatus.BAD_REQUEST);
        }
    }

}
