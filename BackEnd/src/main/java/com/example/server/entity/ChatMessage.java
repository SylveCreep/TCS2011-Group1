package com.example.server.entity;

import javax.persistence.*;

@Entity
@Table(name="chat_message")
public class ChatMessage extends BaseEntity {
    
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(name = "to_user")
    private Long toUserId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatMessage(){

    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }
}
