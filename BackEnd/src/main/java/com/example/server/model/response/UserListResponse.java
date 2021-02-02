package com.example.server.model.response;

public class UserListResponse {
    private int id;
    private String fullname;
    private int faculty_id;
    private String faculty_name;
    private int role_id;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
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

    public UserListResponse(int id, String fullname, int faculty_id, String faculty_name, int role_id, String email) {
        this.id = id;
        this.fullname = fullname;
        this.faculty_id = faculty_id;
        this.faculty_name = faculty_name;
        this.role_id = role_id;
        this.email = email;
    }
    
}
