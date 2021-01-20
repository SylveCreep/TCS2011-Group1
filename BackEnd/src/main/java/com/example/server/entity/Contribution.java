package com.example.server.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

}
