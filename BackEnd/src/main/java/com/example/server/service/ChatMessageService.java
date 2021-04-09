package com.example.server.service;

import java.util.List;

import com.example.server.entity.ChatMessage;
import com.example.server.model.request.ChatMessageRequest;
import com.example.server.model.response.ChatMessagePagingResponse;
import com.example.server.model.response.ChatMessageResponse;

public interface ChatMessageService {
    ChatMessageResponse create(ChatMessageRequest chatMessageRequest);

    ChatMessageResponse update(ChatMessageRequest chatMessageRequest);

    ChatMessageResponse delete(ChatMessageRequest chatMessageRequest);

    List<ChatMessageResponse> searchByContent(ChatMessageRequest chatMessageRequest);

    ChatMessagePagingResponse findAllChatOfUserAndDesto(ChatMessageRequest chatMessageRequest);
}
