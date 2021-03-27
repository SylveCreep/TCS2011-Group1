package com.example.server.model.response;

import java.util.Date;

public class ContributionResponse {
    private Long id;
    private Long userId;
    private String code;
    private String userName;
    private Long checkedById;
    private String checkedByName;
    private Date publishedAt;
    private Date createdAt;
    private Integer status;
    private Long magazineId;
    private String linkSource;
    private String extension;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getCheckedById() {
        return checkedById;
    }
    public void setCheckedById(Long checkedById) {
        this.checkedById = checkedById;
    }
    public String getCheckedByName() {
        return checkedByName;
    }
    public void setCheckedByName(String checkedByName) {
        this.checkedByName = checkedByName;
    }
    public Date getPublishedAt() {
        return publishedAt;
    }
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Long getMagazineId() {
        return magazineId;
    }
    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }
    public String getLinkSource() {
        return linkSource;
    }
    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }

    public ContributionResponse() {

    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public ContributionResponse(Long id, Long userId, String code, String userName, Long checkedById,
            String checkedByName, Date publishedAt, Date createdAt, Integer status, Long magazineId, String linkSource,
            String extension) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.userName = userName;
        this.checkedById = checkedById;
        this.checkedByName = checkedByName;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.status = status;
        this.magazineId = magazineId;
        this.linkSource = linkSource;
        this.extension = extension;
    }
    
}
