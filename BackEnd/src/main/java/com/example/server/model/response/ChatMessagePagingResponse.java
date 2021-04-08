package com.example.server.model.response;

import java.util.List;

public class ChatMessagePagingResponse {
    private int lastPage;

    private List<ChatMessageResponse> chatMessageResponses;

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<ChatMessageResponse> getChatMessageResponses() {
        return chatMessageResponses;
    }

    public void setChatMessageResponses(List<ChatMessageResponse> chatMessageResponses) {
        this.chatMessageResponses = chatMessageResponses;
    }

    public ChatMessagePagingResponse() {
    }

    
    
}
