package com.example.server.model.request;

public class RoleSearchRequest extends PagingRequest {
    private Long roleId;
    private String name;
    private String code;

    public Long getRoleId(){
        return roleId;
    }

    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public RoleSearchRequest(){

    }
    
    public RoleSearchRequest(Long roleId, String name, String code){
        this.roleId = roleId;
        this.name = name;
        this.code= code;
    }

    public RoleSearchRequest(int limit, int page, String sort, String column, Long roleId, String name, String code){
        super(limit, page, sort, column);
        this.roleId = roleId;
        this.name = name;
        this.code = code;
    }
}
