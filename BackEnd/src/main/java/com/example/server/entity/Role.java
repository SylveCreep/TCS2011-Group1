package com.example.server.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "role")
@Getter @Setter
public class Role extends BaseEntity{
    
    @Column(unique = true)
    private String code;

    @Column
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
