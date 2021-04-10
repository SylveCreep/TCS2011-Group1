package com.example.server.entity;

import java.util.Date;

import javax.persistence.*;

import com.example.server.constant.Constant;


@Entity
@Table(name="magazine")
public class Magazine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;


    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "theme", nullable = false)
    private String theme;

    @Column(name="finished_at", nullable = false)
    private Date finishedAt;

    @Column(name="published_at", nullable = false)
    private Date publishedAt;

    @Column(name="close_at", nullable = true)
    private Date closeAt;

    @Column(nullable = false)
    private int is_deleted = Constant.NOTDELETED;

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
        return finishedAt;
    }

    public void setFinished_at(Date finishedAt) {
        this.finishedAt = finishedAt;
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

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Magazine(){
        
    }

    public Magazine(Long id, String code, Date finishedAt, Date publishedAt, Date closeAt, int is_deleted) {
        this.id = id;
        this.code = code;
        this.finishedAt = finishedAt;
        this.publishedAt = publishedAt;
        this.closeAt = closeAt;
        this.is_deleted = is_deleted;
    }

    public Magazine(String code, Date finishedAt, Date publishedAt, Date closeAt){
        this.code = code;
        this.finishedAt = finishedAt;
        this.publishedAt = publishedAt;
        this.closeAt = closeAt;
    }
}
