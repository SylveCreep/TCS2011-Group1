package com.example.server.model.response;

import java.util.List;

public class MagazineLastPageResponse {
    private int lastPage;
    private List<MagazineResponse> list;

    public int getLastPage(){
        return lastPage;
    }

    public void setLastPage(int lastPage){
        this.lastPage = lastPage;
    }

    public List<MagazineResponse> getList(){
        return list;
    } 

    public void setList(List<MagazineResponse> list) {
        this.list = list;
    }

    public MagazineLastPageResponse(){

    }

    public MagazineLastPageResponse(int lastPage, List<MagazineResponse> list){
        this.lastPage = lastPage;
        this.list = list;
    }
}
