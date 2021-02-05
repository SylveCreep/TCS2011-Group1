package com.example.server.model.request;

import javax.validation.constraints.Size;

public class CreateRole {
    
    //@Size(min = 5, max = 5)
    //private String code;

    @Size(max = 255)
    private String name;

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

    public CreateRole(){

    }

    public CreateRole(@Size(min = 5, max = 5) String code,@Size(max = 255) String name){
        //this.code = code;
        this.name = name;
    }
}
