package com.example.server.dao;

import org.springframework.stereotype.Repository;

import com.example.server.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
