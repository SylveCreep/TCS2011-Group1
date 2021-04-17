package com.example.server.entity;

import java.util.Date;
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

    @Column(name ="faculty_name")
    private String name;

    @OneToMany(mappedBy = "faculty")
    private List<User> users;

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

    public List<Contribution> getContribution() {
        return contribution;
    }

    public void setContribution(List<Contribution> contribution) {
        this.contribution = contribution;
    }

    public Faculty() {
    }

    public Faculty(Long id, int is_deleted, Date created_at, Date updated_at, String code, String name,
            List<User> users, List<Contribution> contribution) {
        super(id, is_deleted, created_at, updated_at);
        this.code = code;
        this.name = name;
        this.users = users;
        this.contribution = contribution;
    }

    public Faculty(String code, String name, List<User> users, List<Contribution> contribution) {
        this.code = code;
        this.name = name;
        this.users = users;
        this.contribution = contribution;
    }



    
}
