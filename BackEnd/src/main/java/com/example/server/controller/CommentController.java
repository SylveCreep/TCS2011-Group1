package com.example.server.controller;

import javax.validation.Valid;

import com.example.server.constant.Constant;
import com.example.server.dao.UserDao;
import com.example.server.entity.Comment;
import com.example.server.entity.User;
import com.example.server.model.request.CommentSearchRequest;
import com.example.server.model.request.CreateComment;
import com.example.server.model.response.CommentLastPageResponse;
import com.example.server.model.response.CommentMessageResponse;
import com.example.server.model.response.CommentResponse;
import com.example.server.service.CommentService;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.SessionUtils.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private UserDao userDao;

    @Autowired
    private KafkaTemplate<String, CommentMessageResponse> kafkaCommentTemplate;

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CreateComment comment) {
        try {
            Comment createComment = commentService.saveComment(comment);
            if (createComment == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Create comment failed",
                        HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Create comment successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Create comment fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", consumes = { "test/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has comment id",
                        HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = commentService.deleteComment(id);
            if (is_deleted == false) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Delete comment fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Delete comment successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("Null", Constant.FAILURE, "Delete comment fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(consumes = { "test/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> updateComment(@RequestBody CreateComment commentDto) {
        try {
            if (commentDto.getId() == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has comment id",
                        HttpStatus.BAD_REQUEST);
            }
            Boolean comment = commentService.updateComment(commentDto);
            if (comment == false) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update comment fail",
                        HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(comment, Constant.SUCCESS, "Update comment successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update comment fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<?> showCommentBySearch(@RequestBody CommentSearchRequest commentSearchRequest) {
        try {
            if (commentSearchRequest.getLimit() < 0 || commentSearchRequest.getPage() < 0) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE,
                        "Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            CommentLastPageResponse comment = commentService.searchCommentByUser(commentSearchRequest);
            if (comment == null) {
                return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Don't have comment", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(comment, Constant.SUCCESS, "Show comment success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Show comment failed",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> getComment(@PathVariable(name = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has comment id",
                        HttpStatus.BAD_REQUEST);
            }
            CommentResponse commentResponse = commentService.findCommentById(id);
            if (commentResponse == null) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get comment fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(commentResponse, Constant.SUCCESS, "Get comment successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get comment fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/send", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> sendComment(@RequestBody CommentMessageResponse commentMessage) {
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
            commentMessage.setAvatar(user.getAvatar());
            commentMessage.setCreatedAt(new Date());
            kafkaCommentTemplate.send(KAFKA_TOPIC_COMMENT, commentMessage);
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Send comment successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Send comment fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateComment(@RequestBody CommentMessageResponse commentMessage) {
        try {
            User user = userDao.findExistedUserByEmail(getEmail());
            commentMessage.setUserId(user.getId());
            commentMessage.setUsername(user.getFullName());

            CreateComment comment = new CreateComment();
            comment.setId(commentMessage.getId());
            comment.setContent(commentMessage.getContent());
            comment.setContributionId(commentMessage.getContributionId());
            comment.setParentId(commentMessage.getParentId());
            comment.setUserId(user.getId());
            Boolean isUpdated = commentService.updateComment(comment);
            if (isUpdated == false) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update comment fail",
                        HttpStatus.BAD_REQUEST);
            }
            commentMessage.setAvatar(user.getAvatar());
            commentMessage.setCreatedAt(new Date());
            kafkaCommentTemplate.send(KAFKA_TOPIC_COMMENT, commentMessage);
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Update comment successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update comment fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteComment(@RequestBody CommentMessageResponse commentMessage) {
        try {
            User user = userDao.findExistedUserByEmail(getEmail());
            commentMessage.setUserId(user.getId());
            commentMessage.setUsername(user.getFullName());

            CreateComment comment = new CreateComment();
            comment.setContent(commentMessage.getContent());
            comment.setContributionId(commentMessage.getContributionId());
            comment.setParentId(commentMessage.getParentId());
            comment.setUserId(commentMessage.getUserId());
            Boolean isDeleted = commentService.deleteComment(commentMessage.getId());
            if (isDeleted == false) {
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update comment fail",
                        HttpStatus.BAD_REQUEST);
            }
            commentMessage.setAvatar(user.getAvatar());
            commentMessage.setCreatedAt(new Date());
            kafkaCommentTemplate.send(KAFKA_TOPIC_COMMENT, commentMessage);
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Delete comment successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Delete comment fail",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
