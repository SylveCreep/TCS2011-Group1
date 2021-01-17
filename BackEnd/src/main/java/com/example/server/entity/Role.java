package com.example.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter @Setter
public class Role extends BaseEntity{
    
    @Column(unique = true)
    private String code;

    @Column
    private String name;
}
