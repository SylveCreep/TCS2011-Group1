package com.example.server.model.response;

import java.util.List;

public class FacultyPagingResponse {
    private int lastPage;
    private List<FacultyResponse> facultyResponses;

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<FacultyResponse> getFacultyResponses() {
        return facultyResponses;
    }

    public void setFacultyResponses(List<FacultyResponse> facultyResponses) {
        this.facultyResponses = facultyResponses;
    }

    public FacultyPagingResponse() {
    }
    
}
