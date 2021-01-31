package com.example.server.model.request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Size;

import com.example.server.util.NameValidation;

public class CreateAccount {
    private String email;
    
    @Size(min = 6, max = 30)
    private String password;

    @Size(max = 255)
    private String fullName;

    @Size(max = 255)
    private String address;

    private Date dateOfBirth;

    private int roleId;

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public CreateAccount() {
    }

    public CreateAccount(String email, @Size(min = 6, max = 30) String password, @Size(max = 255) String fullName,
            @Size(max = 255) String address, Date dateOfBirth, int roleId) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.roleId = roleId;
    }
    
}
