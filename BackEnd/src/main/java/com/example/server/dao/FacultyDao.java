package com.example.server.dao;

import java.util.List;

import com.example.server.entity.Faculty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacultyDao extends JpaRepository<Faculty, Long> {
    
    @Query("select f from Faculty f "+ 
    "where f.is_deleted = 0 "+ 
    "group by f.id ")
    List<Faculty> getNonDelFaculty();
}
