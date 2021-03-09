package com.example.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.server.dao.CommentDao;
import com.example.server.entity.Comment;

import org.apache.tomcat.util.http.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.collections.List;

@Service(value = "commentService")
public class CommentService {
    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ModelMapper modelMapper;

    public List<Comment> findAll(){
        List<Comment> list = new ArrayList<>();
        commentDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public Comment findOne(String )
}
