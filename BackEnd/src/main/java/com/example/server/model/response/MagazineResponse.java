package com.example.server.model.response;

import java.util.Date;

public class MagazineResponse {
    private Long id;
    private String code;
    private String theme;
    //private Date openAt;
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

    /*public Date getOpen_at() {
        return openAt;
    }

    public void setOpen_at(Date openAt) {
        this.openAt = openAt;
    }*/

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
