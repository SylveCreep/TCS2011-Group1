package com.example.server.model.response;

public class TotalCountResponse {
    private Long totalContributions;
    private Long totalStudents;
    private Long totalMagazines;

    public Long getTotalContributions() {
        return totalContributions;
    }

    public void setTotalContributions(Long totalContributions) {
        this.totalContributions = totalContributions;
    }

    public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Long getTotalMagazines() {
        return totalMagazines;
    }

    public void setTotalMagazines(Long totalMagazines) {
        this.totalMagazines = totalMagazines;
    }

    public TotalCountResponse() {
    }

}
