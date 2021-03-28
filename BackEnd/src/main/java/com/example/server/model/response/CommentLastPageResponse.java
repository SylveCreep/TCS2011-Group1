package com.example.server.model.response;

import java.util.List;

public class CommentLastPageResponse {
    private int lastPage;
    private List<CommentResponse> list;

    public int getLastPage(){
        return lastPage;
    }

    public void setLastPage(int lastPage){
        this.lastPage = lastPage;
    }

    public List<CommentResponse> getList(){
        return list;
    } 

    public void setList(List<CommentResponse> list) {
        this.list = list;
    }

    public CommentLastPageResponse(){

    }

    public CommentLastPageResponse(int lastPage, List<CommentResponse> list){
        this.lastPage = lastPage;
        this.list = list;
    }
}
