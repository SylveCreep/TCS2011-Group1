package com.example.server.model.request;

import java.util.Date;

public class MagazineSearchRequest extends PagingRequest {
    private Long id;
    private String code;
    private String theme;
    //private Date openAt;
    //private Date publishedAt;
    //private Date closeAt;
    private Date currentDate = new Date();
    private int status;
    
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
    
    public Date getCurrentDate(){
        return currentDate;
    }

    public void setCurrentDate(){
        this.currentDate = new Date();
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    /*public Date getOpen_at() {
        return openAt;
    }

    public void setOpen_at(Date openAt) {
        this.openAt = openAt;
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
    }*/

    public MagazineSearchRequest(){}

    public MagazineSearchRequest(Long id/*, Date openAt, Date closeAt, Date publishedAt*/, String theme, int status){
        this.id = id;
        //this.openAt = openAt;
        //this.publishedAt = publishedAt;
        //this.closeAt = closeAt;
        this.theme = theme;
        this.currentDate = new Date();
        this.status = status;
    }

    public MagazineSearchRequest(int limit, int page, String sort, String column, Long id/*, Date openAt, Date closeAt, Date publishedAt*/, String theme, int status){
        super(limit, page, sort, column);
        this.id = id;
        //this.openAt = openAt;
        //this.closeAt = closeAt;
        //this.publishedAt = publishedAt;
        this.theme = theme;
        this.currentDate = new Date();
        this.status = status;
    }
}
