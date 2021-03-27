package com.example.server.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ContributionRequest extends PagingRequest{
    private Long id;
    private String code;
    private String studentName;
    private Integer status;
    private Long facultyId;
    private Long studentId;
    private Long magazineId;
    private Long checkedBy;


    private Long userId;
    private String linkSource;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    public Integer withFile;


    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public Long getFacultyId() {
        return facultyId;
    }
    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public ContributionRequest(){

    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Long getMagazineId() {
        return magazineId;
    }
    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

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
    public String getLinkSource() {
        return linkSource;
    }
    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }
    public Date getPublishedAt() {
        return publishedAt;
    }
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
    public Integer getWithFile() {
        return withFile;
    }
    public void setWithFile(Integer withFile) {
        this.withFile = withFile;
    }
    public Long getCheckedBy() {
        return checkedBy;
    }
    public void setCheckedBy(Long checkedBy) {
        this.checkedBy = checkedBy;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public ContributionRequest(Long id, String code, String studentName, Integer status, Long facultyId, Long studentId,
            Long magazineId, Long checkedBy, Long userId, String linkSource, Date publishedAt, Date createdAt,
            Integer withFile) {
        this.id = id;
        this.code = code;
        this.studentName = studentName;
        this.status = status;
        this.facultyId = facultyId;
        this.studentId = studentId;
        this.magazineId = magazineId;
        this.checkedBy = checkedBy;
        this.userId = userId;
        this.linkSource = linkSource;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.withFile = withFile;
    }
    public ContributionRequest(int limit, int page, String sort, String column, Long id, String code,
            String studentName, Integer status, Long facultyId, Long studentId, Long magazineId, Long checkedBy,
            Long userId, String linkSource, Date publishedAt, Date createdAt, Integer withFile) {
        super(limit, page, sort, column);
        this.id = id;
        this.code = code;
        this.studentName = studentName;
        this.status = status;
        this.facultyId = facultyId;
        this.studentId = studentId;
        this.magazineId = magazineId;
        this.checkedBy = checkedBy;
        this.userId = userId;
        this.linkSource = linkSource;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.withFile = withFile;
    }
    
    
    
}
