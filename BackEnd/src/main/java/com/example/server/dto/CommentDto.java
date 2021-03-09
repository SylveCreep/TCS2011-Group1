package com.example.server.dto;

import javax.validation.constraints.Size;

import com.example.server.entity.Comment;
import com.example.server.entity.Contribution;
import com.example.server.entity.User;
import com.example.server.util.NameValidation;

public class CommentDto {
    private Long id;

    @Size(max =  255)
    private String content;

    private User user;

    private Contribution contribution;

    public Comment getCommentFromDto(){
        Comment comment = new Comment();
        comment.setContent(content);
        //comment.setUser(user);
        //comment.setContribution(contribution);
        return comment;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        //if(!NameValidation.containSpecialCharacter(content)){
            this.content = content;
        //} else{
        //    this.contribution = null;
        //}
    }

    public User getUser (){
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

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
