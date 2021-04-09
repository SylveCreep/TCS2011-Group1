package com.example.server.model.request;

import static com.example.server.constant.Constant.*;

public class ChatMessageRequest extends PagingRequest {
    private Long id;
    private Long toUser;
    private String content;
    private Integer is_deleted = NOTDELETED;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getToUser() {
        return toUser;
    }
    public void setToUser(Long toUser) {
        this.toUser = toUser;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getIs_deleted() {
        return is_deleted;
    }
    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
    public ChatMessageRequest() {
    }
    public ChatMessageRequest(Long id, Long toUser, String content, Integer is_deleted) {
        this.id = id;
        this.toUser = toUser;
        this.content = content;
        this.is_deleted = is_deleted;
    }
    public ChatMessageRequest(int limit, int page, String sort, String column, Long id, Long toUser, String content,
            Integer is_deleted) {
        super(limit, page, sort, column);
        this.id = id;
        this.toUser = toUser;
        this.content = content;
        this.is_deleted = is_deleted;
    }
}
