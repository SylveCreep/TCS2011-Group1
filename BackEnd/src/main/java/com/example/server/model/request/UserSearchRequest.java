package com.example.server.model.request;

public class UserSearchRequest extends PagingRequest {
    private int userId;
    private int roleId;
    private int facultyId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public UserSearchRequest(int userId, int roleId, int facultyId) {
        this.userId = userId;
        this.roleId = roleId;
        this.facultyId = facultyId;
    }

    public UserSearchRequest(int limit, int page, String sort, String column, int userId, int roleId, int facultyId) {
        super(limit, page, sort, column);
        this.userId = userId;
        this.roleId = roleId;
        this.facultyId = facultyId;
    }

    public UserSearchRequest(){
        
    }

    
}
