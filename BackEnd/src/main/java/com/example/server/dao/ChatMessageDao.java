package com.example.server.dao;

import java.util.List;

import com.example.server.entity.ChatMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageDao extends JpaRepository<ChatMessage, Long> {

    @Query(value ="SELECT c.* from chat_message c WHERE c.is_deleted = 0 AND c.id = :id ", nativeQuery = true)
    ChatMessage findExistedMessageById(@Param("id")Long id);

    @Query(value ="SELECT c.* from chat_message c WHERE c.is_deleted = 0 AND c.content LIKE %:content% AND c.user_id = :userId AND c.to_user = :toUserId ", nativeQuery = true)
    List<ChatMessage> findExistedMessageByContent(@Param("userId")Long userId, @Param("content")String content,@Param("toUserId")Long toUserId);

    @Query(value ="SELECT c.* from chat_message c WHERE c.is_deleted = 0 AND ((c.to_user = :toUserId AND c.user_id = :userId) OR (c.to_user = :userId AND c.user_id = :toUserId))", nativeQuery = true)
    Page<ChatMessage> loadMessageByUserAndDestination(@Param("userId")Long userId,@Param("toUserId")Long toUserId, Pageable pageable);
    
}
