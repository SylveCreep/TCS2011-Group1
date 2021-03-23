package com.example.server.model.request;

import java.util.Date;

public class ContributionRequest  extends PagingRequest{
    private String code;
    private String studentName;
    private Integer status;
    private Long facultyId;
    private Long studentId;
    private Long magazineId;
    private Date submitDate;

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
    public Date getSubmitDate() {
        return submitDate;
    }
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
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
    public ContributionRequest(String code, String studentName, Integer status, Long facultyId, Long studentId,
            Long magazineId, Date submitDate) {
        this.code = code;
        this.studentName = studentName;
        this.status = status;
        this.facultyId = facultyId;
        this.studentId = studentId;
        this.magazineId = magazineId;
        this.submitDate = submitDate;
    }
    public ContributionRequest(int limit, int page, String sort, String column, String code, String studentName,
            Integer status, Long facultyId, Long studentId, Long magazineId, Date submitDate) {
        super(limit, page, sort, column);
        this.code = code;
        this.studentName = studentName;
        this.status = status;
        this.facultyId = facultyId;
        this.studentId = studentId;
        this.magazineId = magazineId;
        this.submitDate = submitDate;
    }
    
}
