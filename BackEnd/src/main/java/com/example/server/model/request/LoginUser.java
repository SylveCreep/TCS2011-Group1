package com.example.server.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginUser {
    private String email;
    private String password;
}
