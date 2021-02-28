package com.example.server.dto;


public class AuthToken {
    private String token;

    private int id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthToken() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AuthToken(String token, int id) {
        this.token = token;
        this.id = id;
    }

}
