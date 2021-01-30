package com.example.server.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.example.server.constant.Constant.IntConstant;

@Entity
@Table(name="magazine")
public class Magazine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;


    @Column(nullable = false, unique = true)
    private String code;

    @Column(name="submit_at")
    private Date submitAt;

    @Column(name="published_at", nullable = false)
    private Date publishedAt;

    @Column(nullable = false)
    private int is_deleted = IntConstant.NOTDELETED;

    @OneToMany(mappedBy = "magazine")
    private List<Contribution> contribution;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getSubmitAt() {
        return submitAt;
    }

    public void setSubmitAt(Date submitAt) {
        this.submitAt = submitAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public List<Contribution> getContribution() {
        return contribution;
    }

    public void setContribution(List<Contribution> contribution) {
        this.contribution = contribution;
    }

    public Magazine(int id, String code, Date submitAt, Date publishedAt, int is_deleted,
            List<Contribution> contribution) {
        this.id = id;
        this.code = code;
        this.submitAt = submitAt;
        this.publishedAt = publishedAt;
        this.is_deleted = is_deleted;
        this.contribution = contribution;
    }
}
