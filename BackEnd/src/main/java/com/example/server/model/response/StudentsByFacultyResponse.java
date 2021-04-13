package com.example.server.model.response;

public class StudentsByFacultyResponse {
    private int totalStudents;
    private String facultyName;

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public StudentsByFacultyResponse() {
    }

    public StudentsByFacultyResponse(int totalStudents, String facultyName) {
        this.totalStudents = totalStudents;
        this.facultyName = facultyName;
    }

}
