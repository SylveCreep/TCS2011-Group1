package com.example.server.controller;

import com.example.server.dao.UserDao;
import com.example.server.entity.ChatMessage;
import com.example.server.entity.Comment;
import com.example.server.entity.User;
import com.example.server.model.request.ChatMessageRequest;
import com.example.server.model.request.CreateComment;
import com.example.server.model.response.ChatMessagePagingResponse;
import com.example.server.model.response.ChatMessageResponse;
import com.example.server.model.response.CommentMessageResponse;
import com.example.server.service.ChatMessageService;
import com.example.server.service.CommentService;
import com.example.server.util.ResponseUtils;

import static java.lang.String.format;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.SessionUtils.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ChatController {

    @Autowired
    private KafkaTemplate<String, ChatMessageResponse> kafkaChatTemplate;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private UserDao userDao;

    @PostMapping(value = "/chat/send", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessageRequest chatMessage) {
        try {
            ChatMessageResponse createdMessage = chatMessageService.create(chatMessage);
            if (createdMessage == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Send message failed", HttpStatus.BAD_REQUEST);
            }
            kafkaChatTemplate.send(KAFKA_TOPIC_CHAT, createdMessage);
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Send message successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Send message failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/chat/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateMessage(@RequestBody ChatMessageRequest chatMessage) {
        try {
            ChatMessageResponse updatedMessage = chatMessageService.update(chatMessage);
            if (updatedMessage == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Update message failed",
                        HttpStatus.BAD_REQUEST);
            }
            kafkaChatTemplate.send(KAFKA_TOPIC_CHAT, updatedMessage);
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Update message successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Update message failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/chat/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteMessage(@RequestBody ChatMessageRequest chatMessage) {
        try {
            ChatMessageResponse deletedMessage = chatMessageService.delete(chatMessage);
            if (deletedMessage == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Delete message failed",
                        HttpStatus.BAD_REQUEST);
            }
            kafkaChatTemplate.send(KAFKA_TOPIC_CHAT, deletedMessage);
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Delete message successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Delete message failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/chat/get", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getMessageBetweenCurrentUserAndToUser(@RequestBody ChatMessageRequest chatMessage) {
        try {
            chatMessage.setPage(chatMessage.getPage() - 1);
            ChatMessagePagingResponse chatMessagePagingResponse = chatMessageService
                    .findAllChatOfUserAndDesto(chatMessage);
            if (chatMessagePagingResponse == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Get message failed", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(chatMessagePagingResponse, SUCCESS, "Get message successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Get message failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/chat/search", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> searchMessageBetweenCurrentUserAndToUser(@RequestBody ChatMessageRequest chatMessage) {
        try {
            if (chatMessage.getContent().isEmpty() || chatMessage.getContent().isBlank()
                    || chatMessage.getContent() == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Content invalid", HttpStatus.BAD_REQUEST);
            }
            List<ChatMessageResponse> chatMessageList = chatMessageService.searchByContent(chatMessage);
            if (chatMessageList == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Get message failed", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(chatMessageList, SUCCESS, "Get message successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Get message failed", HttpStatus.BAD_REQUEST);
        }
    }

}
