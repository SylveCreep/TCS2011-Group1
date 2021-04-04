package com.example.server.model.response;

import java.util.Date;

public class ContributionResponse {
    private Long id;
    private Long userId;
    private Long facultyId;
    private String facultyName;
    private String code;
    private String studentName;
    private Long checkedById;
    private String checkedByName;
    private Date createdAt;
    private Integer status;
    private Long magazineId;
    private String linkSource;
    private String extension;
    private String email;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getCheckedById() {
        return checkedById;
    }
    public void setCheckedById(Long checkedById) {
        this.checkedById = checkedById;
    }
    public String getCheckedByName() {
        return checkedByName;
    }
    public void setCheckedByName(String checkedByName) {
        this.checkedByName = checkedByName;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Long getMagazineId() {
        return magazineId;
    }
    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }
    public String getLinkSource() {
        return linkSource;
    }
    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }

    public ContributionResponse() {

    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public Long getFacultyId() {
        return facultyId;
    }
    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
    public String getFacultyName() {
        return facultyName;
    }
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public ContributionResponse(Long id, Long userId, Long facultyId, String facultyName, String code,
            String studentName, Long checkedById, String checkedByName, Date createdAt, Integer status, Long magazineId,
            String linkSource, String extension, String email) {
        this.id = id;
        this.userId = userId;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.code = code;
        this.studentName = studentName;
        this.checkedById = checkedById;
        this.checkedByName = checkedByName;
        this.createdAt = createdAt;
        this.status = status;
        this.magazineId = magazineId;
        this.linkSource = linkSource;
        this.extension = extension;
        this.email = email;
    }
    
    
    
    
}
