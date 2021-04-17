package com.example.server.service.impl;

import com.example.server.model.request.ChatMessageRequest;
import com.example.server.model.response.ChatMessagePagingResponse;
import com.example.server.model.response.ChatMessageResponse;
import com.example.server.service.ChatMessageService;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.SessionUtils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.server.dao.ChatMessageDao;
import com.example.server.dao.UserDao;
import com.example.server.entity.ChatMessage;
import com.example.server.entity.User;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    UserDao userDao;

    @Autowired
    ChatMessageDao chatMessageDao;

    @Autowired
    ResponseUtils responseUtils;

    @Override
    public ChatMessageResponse create(ChatMessageRequest chatMessageRequest) {
        User user = userDao.findExistedUserByEmail(getEmail());
        User toUser = userDao.findExistedUserById(chatMessageRequest.getToUser());
        if(toUser == null){
            return null;
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFromUser(user);
        chatMessage.setContent(chatMessageRequest.getContent());
        chatMessage.setToUserId(chatMessageRequest.getToUser());
        ChatMessage createdChatMessage = chatMessageDao.save(chatMessage);

        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        chatMessageResponse.setId(createdChatMessage.getId());
        chatMessageResponse.setFromUserId(createdChatMessage.getFromUser().getId());
        chatMessageResponse.setToUserName(toUser.getEmail());
        chatMessageResponse.setToUserId(createdChatMessage.getToUserId());
        chatMessageResponse.setAvatar(createdChatMessage.getFromUser().getAvatar());
        chatMessageResponse.setIs_deleted(createdChatMessage.getIs_deleted());
        chatMessageResponse.setCreatedAt(createdChatMessage.getCreated_at());
        chatMessageResponse.setUpdateAt(createdChatMessage.getUpdated_at());
        chatMessageResponse.setContent(createdChatMessage.getContent());
        return chatMessageResponse;
    }

    @Override
    public ChatMessageResponse update(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = chatMessageDao.findExistedMessageById(chatMessageRequest.getId());
        chatMessage.setContent(chatMessageRequest.getContent());
        chatMessage.setUpdated_at(new Date());
        chatMessageDao.save(chatMessage);

        User toUser = userDao.findExistedUserById(chatMessageRequest.getToUser());
        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        chatMessageResponse.setId(chatMessage.getId());
        chatMessageResponse.setFromUserId(chatMessage.getFromUser().getId());
        chatMessageResponse.setToUserName(toUser.getEmail());
        chatMessageResponse.setToUserId(chatMessage.getToUserId());
        chatMessageResponse.setAvatar(chatMessage.getFromUser().getAvatar());
        chatMessageResponse.setIs_deleted(chatMessage.getIs_deleted());
        chatMessageResponse.setCreatedAt(chatMessage.getCreated_at());
        chatMessageResponse.setUpdateAt(chatMessage.getUpdated_at());
        chatMessageResponse.setContent(chatMessage.getContent());
        return chatMessageResponse;
    }

    @Override
    public ChatMessageResponse delete(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = chatMessageDao.findExistedMessageById(chatMessageRequest.getId());
        chatMessage.setIs_deleted(DELETED);
        chatMessageDao.save(chatMessage);

        User toUser = userDao.findExistedUserById(chatMessage.getToUserId());
        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        chatMessageResponse.setId(chatMessage.getId());
        chatMessageResponse.setFromUserId(chatMessage.getFromUser().getId());
        chatMessageResponse.setToUserName(toUser.getEmail());
        chatMessageResponse.setToUserId(chatMessage.getToUserId());
        chatMessageResponse.setAvatar(chatMessage.getFromUser().getAvatar());
        chatMessageResponse.setIs_deleted(chatMessage.getIs_deleted());
        chatMessageResponse.setCreatedAt(chatMessage.getCreated_at());
        chatMessageResponse.setUpdateAt(chatMessage.getUpdated_at());
        chatMessageResponse.setContent(chatMessage.getContent());
        return chatMessageResponse;
    }

    @Override
    public List<ChatMessageResponse> searchByContent(ChatMessageRequest chatMessageRequest) {
        try {
            User user = userDao.findExistedUserByEmail(getEmail());
            User toUser = userDao.findExistedUserById(chatMessageRequest.getToUser());
            if(toUser == null){
                return null;
            }
            List<ChatMessage> chatMessageList = chatMessageDao.findExistedMessageByContent(user.getId(), chatMessageRequest.getContent(), toUser.getId());
            List<ChatMessageResponse> chatMessageResponseList = new ArrayList<>();
            for(ChatMessage chatMessage: chatMessageList){
                ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
                chatMessageResponse.setId(chatMessage.getId());
                chatMessageResponse.setFromUserId(chatMessage.getFromUser().getId());
                chatMessageResponse.setToUserName(toUser.getEmail());
                chatMessageResponse.setToUserId(chatMessage.getToUserId());
                chatMessageResponse.setAvatar(chatMessage.getFromUser().getAvatar());
                chatMessageResponse.setIs_deleted(chatMessage.getIs_deleted());
                chatMessageResponse.setCreatedAt(chatMessage.getCreated_at());
                chatMessageResponse.setUpdateAt(chatMessage.getUpdated_at());
                chatMessageResponse.setContent(chatMessage.getContent());
                chatMessageResponseList.add(chatMessageResponse);
            }
            return chatMessageResponseList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ChatMessagePagingResponse findAllChatOfUserAndDesto(ChatMessageRequest chatMessageRequest) {
        try {
            User user = userDao.findExistedUserByEmail(getEmail());
            User toUser = userDao.findExistedUserById(chatMessageRequest.getToUser());
            if(toUser == null){
                return null;
            }
            Sort sort = responseUtils.getSortObj(chatMessageRequest);
            Page<ChatMessage> chatMessagePage = chatMessageDao.loadMessageByUserAndDestination(user.getId(), toUser.getId(), PageRequest.of(chatMessageRequest.getPage(), chatMessageRequest.getLimit(), sort));
            int lastPage = Math.round(chatMessagePage.getTotalElements() / chatMessageRequest.getLimit()
                        + ((chatMessagePage.getTotalElements() % chatMessageRequest.getLimit() == 0) ? 0 : 1));
            ChatMessagePagingResponse chatMessagePagingResponse = new ChatMessagePagingResponse();
            List<ChatMessageResponse> chatMessageResponseList = new ArrayList<>();
            for(ChatMessage chatMessage: chatMessagePage){
                ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
                chatMessageResponse.setId(chatMessage.getId());
                chatMessageResponse.setFromUserId(chatMessage.getFromUser().getId());
                chatMessageResponse.setToUserName(toUser.getEmail());
                chatMessageResponse.setToUserId(chatMessage.getToUserId());
                chatMessageResponse.setAvatar(chatMessage.getFromUser().getAvatar());
                chatMessageResponse.setIs_deleted(chatMessage.getIs_deleted());
                chatMessageResponse.setCreatedAt(chatMessage.getCreated_at());
                chatMessageResponse.setUpdateAt(chatMessage.getUpdated_at());
                chatMessageResponse.setContent(chatMessage.getContent());
                chatMessageResponseList.add(chatMessageResponse);
            }
            chatMessagePagingResponse.setChatMessageResponses(chatMessageResponseList);
            chatMessagePagingResponse.setLastPage(lastPage);
            return chatMessagePagingResponse;
        } catch (Exception e) {
            return null;
        }
    }
    
}
