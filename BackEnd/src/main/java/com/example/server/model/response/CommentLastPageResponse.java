package com.example.server.model.response;

import java.util.List;

public class CommentLastPageResponse {
    private int lastPage;
    private Long totalElements;
    private List<CommentResponse> list;

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<CommentResponse> getList() {
        return list;
    }

    public void setList(List<CommentResponse> list) {
        this.list = list;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public CommentLastPageResponse(int lastPage, Long totalElements, List<CommentResponse> list) {
        this.lastPage = lastPage;
        this.totalElements = totalElements;
        this.list = list;
    }

    public CommentLastPageResponse() {
    }

}
