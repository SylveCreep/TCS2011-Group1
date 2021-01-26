package com.example.server.dao;

import com.example.server.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Long>{
    
}
