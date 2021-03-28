package com.example.server.model.request;

public class CommentSearchRequest extends PagingRequest {
    private Long id;

    private String content;

    private Long userId;

    private Long contributionId;

    private String code;

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

    public String getCode(){
        return code;
    }

    public void setCode(){
        this.code = code;
    }

    public CommentSearchRequest(Long id, String content, Long userId, Long contributionId, String code){
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.contributionId = contributionId;
        this.code = code;
    }

    public CommentSearchRequest(int limit, int page, String sort, String column ,Long id, String content, Long userId, Long contributionId, String code){
        super(limit, page, sort, column);
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.contributionId = contributionId;
        this.code = code;
    }

    public CommentSearchRequest(){

    }
}
