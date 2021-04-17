package com.example.server.model.request;

public class PagingRequest {
    public int limit;
    public int page;
    public String sort;
    public String column;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public PagingRequest() {
    }

    public PagingRequest(int limit, int page, String sort, String column) {
        this.limit = limit;
        this.page = page;
        this.sort = sort;
        this.column = column;
    }
    
}
