package com.example.server.dto;

import javax.validation.constraints.Size;

import com.example.server.entity.Role;

import org.springframework.stereotype.Component;

@Component
public class RoleDto {

    private Long id;

    //@Size(min = 5, max = 5)
    //private String code;

    @Size(max = 255)
    private String name;

    public Role getRoleFormDto(){
        Role role = new Role();
        //role.setCode(code);
        role.setName(name);
        return role;
    }
    
    /*public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }*/

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
