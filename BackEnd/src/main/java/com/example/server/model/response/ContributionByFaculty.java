package com.example.server.model.response;

public class ContributionByFaculty {
    private int totalContributions;
    private String facultyName;

    public int getTotalContributions() {
        return totalContributions;
    }

    public void setTotalContributions(int totalContributions) {
        this.totalContributions = totalContributions;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public ContributionByFaculty(int totalContributions, String facultyName) {
        this.totalContributions = totalContributions;
        this.facultyName = facultyName;
    }

    public ContributionByFaculty() {
    }

}
