package com.example.server.entity;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

import static com.example.server.constant.Constant.*;


@MappedSuperclass
@Transactional(readOnly=false)
@Table
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int is_deleted = NOTDELETED;

    @Column(nullable = false)
    private Date created_at = new Date();

    @Column
    private Date updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public BaseEntity() {
    }

    public BaseEntity(int id, int is_deleted, Date created_at, Date updated_at) {
        this.id = id;
        this.is_deleted = is_deleted;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

}
