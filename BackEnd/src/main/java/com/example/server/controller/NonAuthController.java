package com.example.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.example.server.config.TokenProvider;
import com.example.server.dto.AuthToken;
import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.LoginUser;
import com.example.server.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class NonAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;
    
    @Autowired
    private UserService userService;

    private ResponseEntity<?> getResponseEntity(Object data, String code, String mess, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("messenger",mess);
        return new ResponseEntity<>(response, status);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return getResponseEntity(new AuthToken(token, loginUser.getEmail()),"1","Register success", HttpStatus.OK);
        } catch (Exception e) {
            return getResponseEntity("NULL","-1","Login failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value ="/register",  consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto user){
        try {
            userService.saveGuestRegister(user);
            return getResponseEntity("NULL","1","Register success", HttpStatus.OK);
        } catch (Exception e) {
            return getResponseEntity("NULL","-1","Register failed", HttpStatus.BAD_REQUEST);
        }
    }
    
}
