package com.example.server.model.response;

public class UserWithMostContributionResponse {
    private String code;
    private Long studentId;
    private String studentName;
    private String facultyName;
    private Long facultyId;
    private Long totalContribution;
    private String avatar;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public Long getTotalContribution() {
        return totalContribution;
    }

    public void setTotalContribution(Long totalContribution) {
        this.totalContribution = totalContribution;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserWithMostContributionResponse(String code, Long studentId, String studentName, String facultyName,
            Long facultyId, Long totalContribution, String avatar) {
        this.code = code;
        this.studentId = studentId;
        this.studentName = studentName;
        this.facultyName = facultyName;
        this.facultyId = facultyId;
        this.totalContribution = totalContribution;
        this.avatar = avatar;
    }

    public UserWithMostContributionResponse() {
    }

}
