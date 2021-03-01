package com.example.server.model.response;

public class UserListResponse {
    private Long id;
    private String code;
    private String fullname;
    private Long faculty_id;
    private String faculty_name;
    private Long role_id;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getFaculty_id() {
        return faculty_id;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserListResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFaculty_id(Long faculty_id) {
        this.faculty_id = faculty_id;
    }

    public UserListResponse(Long id, String code, String fullname, Long faculty_id, String faculty_name, Long role_id,
            String email) {
        this.id = id;
        this.code = code;
        this.fullname = fullname;
        this.faculty_id = faculty_id;
        this.faculty_name = faculty_name;
        this.role_id = role_id;
        this.email = email;
    }


    
    
}
