package com.example.server.model.response;

import java.util.Date;

public class MagazineResponse {
    private Long id;
    private String code;
    private String theme;
    private Date created_at;
    private Date finished_at;
    private Date publishedAt;
    private Date closeAt;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTheme(){
        return theme;
    }

    public void setTheme(String theme){
        this.theme = theme;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Date finished_at) {
        this.finished_at = finished_at;
    }

    public Date getPublished_at() {
        return publishedAt;
    }

    public void setPublished_at(Date published_at) {
        this.publishedAt = published_at;
    }

    public Date getClose_at(){
        return closeAt;
    }

    public void setClose_at(Date closeAt){
        this.closeAt = closeAt;
    }
}
