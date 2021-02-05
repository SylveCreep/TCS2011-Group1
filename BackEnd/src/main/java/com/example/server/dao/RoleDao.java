package com.example.server.dao;

import java.util.List;

import com.example.server.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleDao extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
    
    Role findRoleById(int id);

    @Query("select r from Role r " +
    "where r.is_deleted = 0 " + 
    "group by r.id")
    List<Role> getNonDelRole();
}
