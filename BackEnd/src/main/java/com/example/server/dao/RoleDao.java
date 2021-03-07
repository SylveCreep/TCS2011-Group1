package com.example.server.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.server.entity.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
    
    Role findRoleById(Long id);

    Role findRoleByCode(String code);

    @Query("select r from Role r " +
    "where r.is_deleted = 0 " + 
    "group by r.id")
    Page<Role> getNonDelRole(Pageable pageable);

    @Query(value = "Select * from role r where r.is_deleted = 0 " +
    "AND ((:name IS NULL) OR LOWER(r.name) LIKE CONCAT('%',IFNULL(LOWER(:name), LOWER(r.name)), '%')) " +
    "AND ((:code IS NULL) OR (r.code = :code))" +
    "AND ((:roleId IS NULL) OR (r.id = :roleId))" +
    "group by r.id", nativeQuery = true)
    Page<Role> searchRoleByName(@Param("roleId") Long roleId, @Param("name") String name, @Param("code") String code, Pageable pageable);
}
