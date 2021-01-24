package com.example.server.controller;
import com.example.server.config.TokenProvider;
import com.example.server.dto.AuthToken;
import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.LoginUser;
import com.example.server.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token, loginUser.getEmail()));
    }

    @PostMapping(value ="/register",  consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> saveUser(@RequestBody UserDto user){
        try {
            userService.save(user);
            return ResponseEntity.ok("Register success");
        } catch (Exception e) {
            return ResponseEntity.ok("Register failed");
        }
    }
    
}
