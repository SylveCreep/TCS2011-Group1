package com.example.server.controller;

import com.example.server.dao.UserDao;
import com.example.server.entity.Comment;
import com.example.server.entity.User;
import com.example.server.model.request.CreateComment;
import com.example.server.model.response.CommentMessageResponse;
import com.example.server.service.CommentService;

import static java.lang.String.format;

import org.springframework.beans.factory.annotation.Autowired;
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
    private KafkaTemplate<String, CommentMessageResponse> kafkaTemplate;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserDao userDao;

    @PostMapping(value = "/comment/send", consumes = "application/json", produces = "application/json")
    public void sendComment(@RequestBody CommentMessageResponse commentMessage) {
        try {
            User user = userDao.findExistedUserByEmail(getEmail());
            commentMessage.setUserId(user.getId());
            commentMessage.setUsername(user.getFullName());

            CreateComment comment = new CreateComment();
            comment.setContent(commentMessage.getContent());
            comment.setContributionId(commentMessage.getContributionId());
            comment.setParentId(commentMessage.getParentId());
            comment.setUserId(commentMessage.getUserId());
            Comment commentEntity = commentService.saveComment(comment);
            commentMessage.setId(commentEntity.getId());
            kafkaTemplate.send(KAFKA_TOPIC_COMMENT, commentMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/comment/update", consumes = "application/json", produces = "application/json")
    public void updateComment(@RequestBody CommentMessageResponse commentMessage) {
        try {
            User user = userDao.findExistedUserByEmail(getEmail());
            commentMessage.setUserId(user.getId());
            commentMessage.setUsername(user.getFullName());

            CreateComment comment = new CreateComment();
            comment.setContent(commentMessage.getContent());
            comment.setContributionId(commentMessage.getContributionId());
            comment.setParentId(commentMessage.getParentId());
            comment.setUserId(commentMessage.getUserId());
            commentService.updateComment(comment);
            kafkaTemplate.send(KAFKA_TOPIC_COMMENT, commentMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/comment/delete", consumes = "application/json", produces = "application/json")
    public void deleteComment(@RequestBody CommentMessageResponse commentMessage) {
        try {
            User user = userDao.findExistedUserByEmail(getEmail());
            commentMessage.setUserId(user.getId());
            commentMessage.setUsername(user.getFullName());

            CreateComment comment = new CreateComment();
            comment.setContent(commentMessage.getContent());
            comment.setContributionId(commentMessage.getContributionId());
            comment.setParentId(commentMessage.getParentId());
            comment.setUserId(commentMessage.getUserId());
            commentService.deleteComment(commentMessage.getId());
            kafkaTemplate.send(KAFKA_TOPIC_COMMENT, commentMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
