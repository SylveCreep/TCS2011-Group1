package com.example.server.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name= "role")
public class Role extends BaseEntity{
    
    @Column(unique = true,length = 5)
    private String code;

    @Column
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Role() {
    }

    public Role(String code, String name, List<User> users) {
        this.code = code;
        this.name = name;
        this.users = users;
    }

    public Role(Long id, int is_deleted, Date created_at, Date updateted_at, String code, String name, List<User> users){
        super(id, is_deleted, created_at, updateted_at);
        this.code = code;
        this.name = name;
        this.users = users;
    }
}
