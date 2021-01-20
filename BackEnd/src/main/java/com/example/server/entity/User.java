package com.example.server.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter @Setter @NoArgsConstructor
public class User extends BaseEntity {
    
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String code;

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
    @JoinColumn(name="role_id", nullable = false)
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



    
}
