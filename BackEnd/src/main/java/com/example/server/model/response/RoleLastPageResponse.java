package com.example.server.model.response;

import java.util.List;

public class RoleLastPageResponse {
    private int lastPage;
    private List<RoleResponse> list;

    public int getLastPage(){
        return lastPage;
    }

    public void setLastPage(int lastPage){
        this.lastPage = lastPage;
    }

    public List<RoleResponse> getList(){
        return list;
    } 

    public void setList(List<RoleResponse> list) {
        this.list = list;
    }

    public RoleLastPageResponse(){

    }

    public RoleLastPageResponse(int lastPage, List<RoleResponse> list){
        this.lastPage = lastPage;
        this.list = list;
    }
}
