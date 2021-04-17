package com.example.server.model.request;

import java.util.Date;

public class UserSearchRequest extends PagingRequest {
    private Long userId;
    private Long roleId;
    private Long facultyId;
    private String fullName;
    private String roleName;
    private String facultyName;
    private Date startDate;
    private Date endDate;
    private String email;
    private Integer gender;
    private String code;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

   

    public UserSearchRequest(){
        
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserSearchRequest(Long userId, Long roleId, Long facultyId, String fullName, String roleName,
            String facultyName, Date startDate, Date endDate, String email, Integer gender, String code) {
        this.userId = userId;
        this.roleId = roleId;
        this.facultyId = facultyId;
        this.fullName = fullName;
        this.roleName = roleName;
        this.facultyName = facultyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.email = email;
        this.gender = gender;
        this.code = code;
    }

    public UserSearchRequest(int limit, int page, String sort, String column, Long userId, Long roleId, Long facultyId,
            String fullName, String roleName, String facultyName, Date startDate, Date endDate, String email,
            Integer gender, String code) {
        super(limit, page, sort, column);
        this.userId = userId;
        this.roleId = roleId;
        this.facultyId = facultyId;
        this.fullName = fullName;
        this.roleName = roleName;
        this.facultyName = facultyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.email = email;
        this.gender = gender;
        this.code = code;
    }

    

    

    

    
}
