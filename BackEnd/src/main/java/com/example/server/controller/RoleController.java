package com.example.server.controller;

import com.example.server.entity.Role;
import com.example.server.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @GetMapping("/findRoleByName")
    public Role findRoleByName (@RequestParam("name") String name){
        return roleService.findByName(name);
    }
}
