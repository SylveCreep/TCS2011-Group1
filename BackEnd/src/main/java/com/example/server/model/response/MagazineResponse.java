package com.example.server.model.response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MagazineResponse {
    private Long id;
    private String code;
    private String theme;
    private String created_at;
    private String finished_at;
    private String published_at;
    private String close_at;

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date createdAt) {
        String created_at = simpleDateFormat.format(createdAt);
        this.created_at = created_at;
    }

    public String getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Date finishedAt) {
        String finished_at = simpleDateFormat.format(finishedAt);
        this.finished_at = finished_at;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(Date publishedAt) {
        String published_at = simpleDateFormat.format(publishedAt);
        this.published_at = published_at;
    }

    public String getClose_at(){
        return close_at;
    }

    public void setClose_at(Date closeAt){
        String close_at;
        if (closeAt != null){
            close_at = simpleDateFormat.format(closeAt);
        } else{
            close_at = "";
        }
        this.close_at = close_at;
    }
}
