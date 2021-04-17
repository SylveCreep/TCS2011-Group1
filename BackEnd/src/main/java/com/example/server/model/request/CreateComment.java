package com.example.server.model.request;

public class CreateComment {
    private Long id;

    private String content;

    private Long userId;

    private Long contributionId;

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

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getContributionId(){
        return contributionId;
    }

    public void setContributionId(Long contributionId){
        this.contributionId = contributionId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
