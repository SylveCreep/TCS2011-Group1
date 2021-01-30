package com.example.server.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.example.server.entity.User;

import org.hibernate.validator.constraints.Length;


public class UserDto {

    private String email;
    
    @Size(min = 6, max = 30)
    private String password;

    @Size(max = 255)
    private String fullName;

    @Size(max = 255)
    private String address;

    private Date dateOfBirth;

    public User getUserFromDto(){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setAddress(address);
        user.setDateOfBirth(dateOfBirth);
        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
