package com.example.server.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.example.server.entity.Contribution;
import com.example.server.entity.Magazine;

import org.springframework.stereotype.Component;

@Component
public class MagazineDto {
    private Long id;

    @Size(min = 5, max = 5)
    private String code;

    private String theme;

    private Date finished_at;

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

    public Date getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Date finished_at) {
        this.finished_at = finished_at;
    }

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

    public Magazine getMagazineFromDto(){
        Magazine magazine = new Magazine();
        magazine.setFinished_at(finished_at);;
        magazine.setPublished_at(published_at);;
        magazine.setClose_at(close_at);
        magazine.setTheme(theme);
        magazine.setCode(code);
        return magazine;
    }
}
