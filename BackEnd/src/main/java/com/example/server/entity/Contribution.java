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

    @OneToMany(mappedBy = "contribution")
    private List<Comment> comments;

    @Column(unique = true, nullable = false,length = 6)
    private String code;

    @Column(name="link_source",nullable = false)
    private String linkSource;

    @Column(name="published_at",nullable = false)
    private Date publishedAt;

    @Column(name="is_approved",nullable = false)
    private int isApproved = IntConstant.NOTAPPROVED;


}
