package com.example.server.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static com.example.server.constant.Constant.*;

@Entity
@Table(name = "contribution")
public class Contribution extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="checked_by", referencedColumnName = "id", nullable = false)
    private User checkedBy;

    @ManyToOne
    @JoinColumn(name="faculty_id",  referencedColumnName = "id", nullable = false)
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name="magazine_id",  referencedColumnName = "id", nullable = false)
    private Magazine magazine;

    @OneToMany(mappedBy = "contribution")
    private List<Comment> comments;

    @Column(unique = true, nullable = false,length = 6)
    private String code;

    @Column(name="link_source",nullable = false)
    private String linkSource;

    @Column(name="published_at",nullable = false)
    private Date publishedAt;

    @Column(name="is_approved",nullable = false)
    private int isApproved = PENDING;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(User checkedBy) {
        this.checkedBy = checkedBy;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLinkSource() {
        return linkSource;
    }

    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public Contribution(User user, User checkedBy, Faculty faculty, Magazine magazine, List<Comment> comments,
            String code, String linkSource, Date publishedAt, int isApproved) {
        this.user = user;
        this.checkedBy = checkedBy;
        this.faculty = faculty;
        this.magazine = magazine;
        this.comments = comments;
        this.code = code;
        this.linkSource = linkSource;
        this.publishedAt = publishedAt;
        this.isApproved = isApproved;
    }

    public Contribution(Long id, int is_deleted, Date created_at, Date updated_at, User user, User checkedBy,
            Faculty faculty, Magazine magazine, List<Comment> comments, String code, String linkSource,
            Date publishedAt, int isApproved) {
        super(id, is_deleted, created_at, updated_at);
        this.user = user;
        this.checkedBy = checkedBy;
        this.faculty = faculty;
        this.magazine = magazine;
        this.comments = comments;
        this.code = code;
        this.linkSource = linkSource;
        this.publishedAt = publishedAt;
        this.isApproved = isApproved;
    }



}
