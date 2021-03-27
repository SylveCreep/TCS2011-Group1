package com.example.server.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import static com.example.server.constant.Constant.*;


@Entity
@Table(name="magazine")
public class Magazine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;


    @Column(nullable = false, unique = true)
    private String code;

    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @Column(name="published_at", nullable = false)
    private Date publishedAt;

    @Column(name="expired_at", nullable = false)
    private Date expiredAt;

    @Column(name="closed_at")
    private Date closedAt;

    @Column(name="theme")
    private String theme;

    @Column(nullable = false)
    private int is_deleted = NOTDELETED;

    @OneToMany(mappedBy = "magazine")
    private List<Contribution> contribution;

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

    public Magazine(){
        
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Magazine(Long id, String code, Date createdAt, Date publishedAt, Date expiredAt, Date closedAt, String theme,
            int is_deleted, List<Contribution> contribution) {
        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
        this.publishedAt = publishedAt;
        this.expiredAt = expiredAt;
        this.closedAt = closedAt;
        this.theme = theme;
        this.is_deleted = is_deleted;
        this.contribution = contribution;
    }
}
