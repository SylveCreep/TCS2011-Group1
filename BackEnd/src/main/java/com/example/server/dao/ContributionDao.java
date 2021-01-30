package com.example.server.dao;

import com.example.server.entity.Contribution;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionDao extends JpaRepository<Contribution, Long> {
    
}
