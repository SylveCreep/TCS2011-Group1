package com.example.server.entity;

import javax.persistence.*;

@Entity
@Table(name="chat_message")
public class ChatMessage extends BaseEntity {
    
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name="from_user",nullable = false)
    private User fromUser;

    @Column(name = "to_user")
    private Long toUserId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatMessage(){

    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }
}
