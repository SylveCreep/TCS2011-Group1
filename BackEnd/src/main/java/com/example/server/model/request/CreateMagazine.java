package com.example.server.model.request;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.example.server.entity.Contribution;

public class CreateMagazine {
    private Long id;

    @Size(min = 5, max = 5)
    private String code;

    private String theme;

    //private Date open_at;

    private Date published_at;

    private Date close_at;

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
        return open_at;
    }

    public void setOpen_at(Date open_at) {
        this.open_at = open_at;
    }*/

    public Date getPublished_at() {
        return published_at;
    }

    public void setPublished_at(Date publishedAt) {
        this.published_at = publishedAt;
    }

    public Date getClose_at(){
        return close_at;
    }

    public void setClose_at(Date close_at){
        this.close_at = close_at;
    }
}
