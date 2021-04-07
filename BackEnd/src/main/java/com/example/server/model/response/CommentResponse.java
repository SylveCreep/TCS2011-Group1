package com.example.server.model.response;

import com.example.server.entity.Contribution;
import com.example.server.entity.User;

public class CommentResponse {
    private Long id;

    private String content;

    private User user;

    private Contribution contribution;

    private String code;

    private Long parentId;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Contribution getContribution(){
        return contribution;
    }

    public void setContribution(Contribution contribution){
        this.contribution = contribution;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
