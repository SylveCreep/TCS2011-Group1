package com.example.server.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Size;

import com.example.server.entity.User;
import com.example.server.util.NameValidation;

import org.hibernate.validator.constraints.Length;


public class UserDto {

    private Long id;

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
        if(!NameValidation.containSpecialCharacter(fullName) && !NameValidation.containNumber(fullName)){
            this.fullName = fullName;
        } else {
            this.fullName = null;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(!NameValidation.containSpecialCharacter(address)){
            this.address = address;
        } else {
            this.address = null;
        }
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date currentDate = new Date();
        if((Integer.parseInt(dateFormat.format(currentDate).substring(0, 4)) - Integer.parseInt(dateFormat.format(dateOfBirth).substring(0, 4))) > 18){
            this.dateOfBirth = dateOfBirth;
        } else {
            this.dateOfBirth = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
