package com.example.server.dao;

import java.util.Date;
import java.util.List;

import com.example.server.entity.Faculty;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyDao extends JpaRepository<Faculty, Long> {

    Faculty findFacultyById(Long id);

    @Query(value = "SELECT f.* FROM faculty f WHERE f.is_deleted = 0 AND f.id = :id ", nativeQuery = true)
    Faculty findExistedFacultyById(Long id);
    
    @Query("select f from Faculty f "+ 
    "where f.is_deleted = 0 "+ 
    "group by f.id ")
    List<Faculty> getNonDelFaculty();

    @Query(value ="SELECT * FROM faculty f "+ 
    "WHERE f.is_deleted = 0 "+
    "AND ((:facultyName IS NULL) OR (f.faculty_name LIKE CONCAT('%',:facultyName,'%'))) "+ 
    "AND ((:code IS NULL) OR (f.code LIKE CONCAT('%',:code,'%'))) "+
    "AND ((:hasDate = 0) OR (f.created_at BETWEEN :startDate AND :endDate)) "+ 
    "group by f.id ", nativeQuery = true)
    Page<Faculty> searchFaculty(@Param("code")String code, @Param("facultyName")String facultyName, @Param("startDate")Date startDate,
    @Param("endDate")Date endDate, @Param("hasDate") int hasDate, Pageable page);
}
