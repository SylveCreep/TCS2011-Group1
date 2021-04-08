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

import static com.example.server.constant.Constant.*;
import static com.example.server.util.SessionUtils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.server.dao.ChatMessageDao;
import com.example.server.dao.UserDao;
import com.example.server.entity.ChatMessage;
import com.example.server.entity.User;

public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    UserDao userDao;

    @Autowired
    ChatMessageDao chatMessageDao;

    @Autowired
    ResponseUtils responseUtils;

    @Override
    public Boolean create(ChatMessageRequest chatMessageRequest) {
        User user = userDao.findExistedUserByEmail(getEmail());
        User toUser = userDao.findExistedUserById(chatMessageRequest.getToUser());
        if(toUser == null){
            return false;
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(user);
        chatMessage.setContent(chatMessageRequest.getContent());
        chatMessage.setToUserId(chatMessageRequest.getToUser());
        chatMessageDao.save(chatMessage);
        return true;
    }

    @Override
    public Boolean update(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = chatMessageDao.findExistedMessageById(chatMessageRequest.getId());
        chatMessage.setContent(chatMessageRequest.getContent());
        chatMessage.setUpdated_at(new Date());
        chatMessageDao.save(chatMessage);
        return true;
    }

    @Override
    public Boolean delete(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = chatMessageDao.findExistedMessageById(chatMessageRequest.getId());
        chatMessage.setIs_deleted(DELETED);
        chatMessageDao.save(chatMessage);
        return true;
    }

    @Override
    public List<ChatMessageResponse> searchByContent(ChatMessageRequest chatMessageRequest) {
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
            chatMessageResponse.setUserId(chatMessage.getUser().getId());
            chatMessageResponse.setToUserId(chatMessage.getToUserId());
            chatMessageResponse.setAvatar(chatMessage.getUser().getAvatar());
            chatMessageResponse.setIs_deleted(chatMessage.getIs_deleted());
            chatMessageResponse.setCreatedAt(chatMessage.getCreated_at());
            chatMessageResponse.setUpdateAt(chatMessage.getUpdated_at());
            chatMessageResponse.setContent(chatMessage.getContent());
            chatMessageResponseList.add(chatMessageResponse);
        }
        return chatMessageResponseList;
    }

    @Override
    public ChatMessagePagingResponse findAllChatOfUserAndDesto(ChatMessageRequest chatMessageRequest) {
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
            chatMessageResponse.setUserId(chatMessage.getUser().getId());
            chatMessageResponse.setToUserId(chatMessage.getToUserId());
            chatMessageResponse.setAvatar(chatMessage.getUser().getAvatar());
            chatMessageResponse.setIs_deleted(chatMessage.getIs_deleted());
            chatMessageResponse.setCreatedAt(chatMessage.getCreated_at());
            chatMessageResponse.setUpdateAt(chatMessage.getUpdated_at());
            chatMessageResponse.setContent(chatMessage.getContent());
            chatMessageResponseList.add(chatMessageResponse);
        }
        chatMessagePagingResponse.setChatMessageResponses(chatMessageResponseList);
        chatMessagePagingResponse.setLastPage(lastPage);
        return chatMessagePagingResponse;
    }
    
}
