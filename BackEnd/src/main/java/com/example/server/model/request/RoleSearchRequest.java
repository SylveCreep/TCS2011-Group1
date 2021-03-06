package com.example.server.model.request;

public class RoleSearchRequest extends PagingRequest {
    private Long roleId;
    private String name;

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

    public RoleSearchRequest(){

    }
    
    public RoleSearchRequest(Long roleId, String name){
        this.roleId = roleId;
        this.name = name;
    }

    public RoleSearchRequest(int limit, int page, String sort, String column, Long roleId, String name){
        super(limit, page, sort, column);
        this.roleId = roleId;
        this.name = name;
    }
}
