package com.example.server.model.response;

public class ContributionByMagazineResponse {
    private int totalContributions;
    private String magazineName;

    public int getTotalContributions() {
        return totalContributions;
    }

    public void setTotalContributions(int totalContributions) {
        this.totalContributions = totalContributions;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }

    public ContributionByMagazineResponse() {
    }

    public ContributionByMagazineResponse(int totalContributions, String magazineName) {
        this.totalContributions = totalContributions;
        this.magazineName = magazineName;
    }

}
