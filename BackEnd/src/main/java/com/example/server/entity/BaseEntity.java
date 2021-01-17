package com.example.server.entity;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@MappedSuperclass
@Transactional(readOnly=false)
@Table
@Getter @Setter @NoArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private Boolean is_deleted = Boolean.FALSE;

    @Column
    private Date created_at;

    @Column
    private Date updateted_at;

}
