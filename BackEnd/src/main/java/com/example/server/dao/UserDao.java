package com.example.server.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.server.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value="Select u.* from user u where u.is_deleted = :deleted", nativeQuery = true)
    List<User> findUserByDeleted(@Param("deleted") int deleted);
}
