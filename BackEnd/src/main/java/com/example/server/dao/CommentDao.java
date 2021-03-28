package com.example.server.dao;

import com.example.server.entity.Comment;
import com.example.server.entity.User;
import com.example.server.model.response.CommentResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentDao extends JpaRepository<Comment, Long>{
    CommentResponse findCommentById(Long id);

    @Query("select r from Role r " +
    "where r.is_deleted = 0 " + 
    "group by r.id")
    Page<Comment> getNonDelRole(Pageable pageable);
    
    @Query(value = "Select * from comment c " + 
    "LEFT JOIN user u ON u.id = c.user_id " +
    //"LEFT JOIN contribution t ON t.id = c.contribution_id " +
    " WHERE c.is_deleted = 0 " +
    "AND ((:id is null) or (c.id = :id)) " + 
    "AND ((:userId IS NULL) OR (u.full_name LIKE CONCAT('%',:userId,'%'))) " +
    //"AND ((:contribution IS NULL) OR (u.full_name LIKE CONCAT('%',:contribution,'%'))) " +
    "AND ((:code IS NULL) OR LOWER (c.code) LIKE CONCAT('%',IFNULL(LOWER(:code),LOWER(c.code)),'%')) " +
    "group by r.id", nativeQuery = true)
    Page<Comment> searchCommentByUser(@Param("id") Long id, @Param("userId") Long userId, @Param("code") String code, Pageable pageable);
}
