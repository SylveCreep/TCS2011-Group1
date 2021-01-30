package com.example.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.service.UserService;

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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    
    @Autowired
    private UserService userService;

    private ResponseEntity<?> getResponseEntity(Object data, String code, String mess, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("messenger",mess);
        return new ResponseEntity<>(response, status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value ="/create",  consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateAccount user){
        try {
            userService.saveRegister(user);
            return getResponseEntity("NULL","1","Register success", HttpStatus.OK);
        } catch (Exception e) {
            return getResponseEntity("NULL","-1","Register failed", HttpStatus.BAD_REQUEST);
        }
    }


}
