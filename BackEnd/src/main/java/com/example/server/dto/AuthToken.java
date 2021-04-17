package com.example.server.dto;


public class AuthToken {
    private String token;

    private Long id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthToken(String token, Long id) {
        this.token = token;
        this.id = id;
    }

}
