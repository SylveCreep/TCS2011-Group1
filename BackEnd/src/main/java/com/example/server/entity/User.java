package com.example.server.entity;

import com.example.server.util.QueryChecking;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="user")
public class User extends BaseEntity {
    // @Autowired
    // private QueryChecking queryChecking;
    
    @Column(unique = true, nullable = false)
    private String email;

    // @Column(unique = true, nullable = false,length = 5)
    // private String code= "U" + String.format("%04d", queryChecking.CheckHighestIdUser("user"));

    @Column(unique = true, nullable = false,length = 5)
    private String code= "U" + String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    //@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JoinTable(name = "USER_ROLES",
    //         joinColumns = {
    //         @JoinColumn(name = "USER_ID")
    //         },
    //         inverseJoinColumns = {
    //         @JoinColumn(name = "ROLE_ID") })
    @ManyToOne
    @JoinColumn(name="role_id",nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculty faculty;

    @Column(nullable = false)
    private String fullName, address;

    @OneToMany(mappedBy = "user")
    private List<Contribution> contributions;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @Column(name="reset_password_key")
    private String resetPasswordKey;

    @Column(name="key_created_at")
    private Date keyCreatedAt;

    @Column(name="date_of_birth",nullable = false)
    private Date dateOfBirth;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getResetPasswordKey() {
        return resetPasswordKey;
    }

    public void setResetPasswordKey(String resetPasswordKey) {
        this.resetPasswordKey = resetPasswordKey;
    }

    public Date getKeyCreatedAt() {
        return keyCreatedAt;
    }

    public void setKeyCreatedAt(Date keyCreatedAt) {
        this.keyCreatedAt = keyCreatedAt;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public User() {
    }
    
    public User(String email, String code, String password, Role role, Faculty faculty, String fullName, String address,
            List<Contribution> contributions, List<Comment> comments, String resetPasswordKey, Date keyCreatedAt,
            Date dateOfBirth) {
        this.email = email;
        this.code = code;
        this.password = password;
        this.role = role;
        this.faculty = faculty;
        this.fullName = fullName;
        this.address = address;
        this.contributions = contributions;
        this.comments = comments;
        this.resetPasswordKey = resetPasswordKey;
        this.keyCreatedAt = keyCreatedAt;
        this.dateOfBirth = dateOfBirth;
    }

    public User(int id, int is_deleted, Date created_at, Date updateted_at, String email, String code, String password,
            Role role, Faculty faculty, String fullName, String address, List<Contribution> contributions,
            List<Comment> comments, String resetPasswordKey, Date keyCreatedAt, Date dateOfBirth) {
        super(id, is_deleted, created_at, updateted_at);
        this.email = email;
        this.code = code;
        this.password = password;
        this.role = role;
        this.faculty = faculty;
        this.fullName = fullName;
        this.address = address;
        this.contributions = contributions;
        this.comments = comments;
        this.resetPasswordKey = resetPasswordKey;
        this.keyCreatedAt = keyCreatedAt;
        this.dateOfBirth = dateOfBirth;
    }
}
