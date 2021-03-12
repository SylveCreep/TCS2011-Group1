package com.example.server.model.response;

public class FacultyResponse {
    private Long facultyId;
    private Long managerId;

    private String code;
    private String facultyName;
    private String managerName;
    
    public Long getFacultyId() {
        return facultyId;
    }
    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
    public Long getManagerId() {
        return managerId;
    }
    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getFacultyName() {
        return facultyName;
    }
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
    public String getManagerName() {
        return managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public FacultyResponse() {
    }
    

}
