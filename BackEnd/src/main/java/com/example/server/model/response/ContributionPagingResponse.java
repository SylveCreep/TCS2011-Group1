package com.example.server.model.response;

import java.util.List;

public class ContributionPagingResponse {
    private List<ContributionResponse> list;
    private int lastPage;

    public List<ContributionResponse> getList() {
        return list;
    }
    public void setList(List<ContributionResponse> list) {
        this.list = list;
    }
    public int getLastPage() {
        return lastPage;
    }
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public ContributionPagingResponse(){
        
    }
    public ContributionPagingResponse(List<ContributionResponse> list, int lastPage) {
        this.list = list;
        this.lastPage = lastPage;
    }
    
}
