package com.example.server.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "faculty")
public class Faculty extends BaseEntity {
    
    @Column(unique = true, nullable = false,length = 5)
    private String code;

    @Column
    private String name;

    @OneToMany(mappedBy = "faculty")
    private List<User> users;

    @OneToOne
    @JoinColumn(name= "manager_id", referencedColumnName = "id")
    private User manager;

    @OneToMany(mappedBy = "faculty")
    private List<Contribution> contribution;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<Contribution> getContribution() {
        return contribution;
    }

    public void setContribution(List<Contribution> contribution) {
        this.contribution = contribution;
    }

    public Faculty() {
    }

    public Faculty(String code, List<User> users, User manager, List<Contribution> contribution) {
        this.code = code;
        this.users = users;
        this.manager = manager;
        this.contribution = contribution;
    }


    
}