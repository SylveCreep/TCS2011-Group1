package com.example.server.service;

import java.util.List;

import com.example.server.dto.CommentDto;
import com.example.server.entity.Comment;
import com.example.server.model.request.CommentSearchRequest;
import com.example.server.model.request.CreateComment;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.response.CommentLastPageResponse;
import com.example.server.model.response.CommentResponse;

public interface CommentService {
    List<Comment> findAll();

    CommentResponse findCommentById(Long id);

    Boolean deleteComment(Long id);

    Boolean updateComment(CreateComment commentDto);

    Comment saveComment(CommentDto comment);

    Comment saveComment(CreateComment comment);

    List<Object> getCommentListResponse(PagingRequest pagingRequest);

    CommentLastPageResponse searchCommentByUser(CommentSearchRequest commentSearchRequest);
}
