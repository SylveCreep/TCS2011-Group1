package com.example.server.dao;

import com.example.server.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
    
    Role findRoleById(int id);
}
