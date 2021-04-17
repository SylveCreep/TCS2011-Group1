package com.example.server.model.response;

import java.util.List;

public class UserLastPageResponse {
    private int lastPage;
    private List<UserResponse> list;

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<UserResponse> getList() {
        return list;
    }

    public void setList(List<UserResponse> list) {
        this.list = list;
    }

    public UserLastPageResponse(){

    }

    public UserLastPageResponse(int lastPage, List<UserResponse> list) {
        this.lastPage = lastPage;
        this.list = list;
    }
}
