package com.example.server.model.response;
import static com.example.server.constant.Constant.*;

import java.util.Date;

public class ChatMessageResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String email;
    private Long toUserId;
    private String content;
    private String avatar;
    private Integer is_deleted = NOTDELETED;
    private Date createdAt;
    private Date updateAt;

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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public Integer getIs_deleted() {
        return is_deleted;
    }
    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Long getToUserId() {
        return toUserId;
    }
    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }
    public ChatMessageResponse() {
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ChatMessageResponse(Long id, Long userId, String userName, String email, Long toUserId, String content,
            String avatar, Integer is_deleted, Date createdAt, Date updateAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.toUserId = toUserId;
        this.content = content;
        this.avatar = avatar;
        this.is_deleted = is_deleted;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    
}
