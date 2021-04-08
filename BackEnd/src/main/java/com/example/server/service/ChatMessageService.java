package com.example.server.service;

import java.util.List;

import com.example.server.model.request.ChatMessageRequest;
import com.example.server.model.response.ChatMessagePagingResponse;
import com.example.server.model.response.ChatMessageResponse;

public interface ChatMessageService {
    Boolean create(ChatMessageRequest chatMessageRequest);

    Boolean update(ChatMessageRequest chatMessageRequest);

    Boolean delete(ChatMessageRequest chatMessageRequest);

    List<ChatMessageResponse> searchByContent(ChatMessageRequest chatMessageRequest);

    ChatMessagePagingResponse findAllChatOfUserAndDesto(ChatMessageRequest chatMessageRequest);
}
