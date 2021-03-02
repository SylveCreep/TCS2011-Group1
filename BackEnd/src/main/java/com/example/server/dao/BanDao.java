package com.example.server.dao;

import com.example.server.entity.BanList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanDao extends JpaRepository<BanList, Long> {
    BanList findByToken(String token);
    
}
