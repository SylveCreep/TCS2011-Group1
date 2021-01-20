package com.example.server.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "faculty")
@Getter @Setter
public class Faculty extends BaseEntity {
    
    @Column(unique = true, nullable = false)
    private String code;

    @OneToMany(mappedBy = "faculty")
    private List<User> users;

    @OneToOne
    @JoinColumn(name= "manager_id", referencedColumnName = "id", nullable = false)
    private User manager;

    @OneToMany(mappedBy = "faculty")
    private List<Contribution> contribution;


    
}
