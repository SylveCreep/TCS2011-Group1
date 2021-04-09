package com.example.server.model.request;

public class ChangePasswordRequest {
    private String password;
    private String new_password;
    private String email;
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNew_password() {
        return new_password;
    }
    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
