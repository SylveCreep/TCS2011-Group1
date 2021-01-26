package com.example.server.dao;

import com.example.server.entity.Faculty;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyDao extends JpaRepository<Faculty, Long> {
    
}
