package com.example.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.example.server.entity.Role;
import com.example.server.model.request.CreateRole;
import com.example.server.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private ResponseEntity<?> getResponseEntity(Object data, String code, String mess, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("messenger",mess);
        return new ResponseEntity<>(response, status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create", consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> createRole(@Valid @RequestBody CreateRole role){
        try{
            roleService.saveRole(role);
            return getResponseEntity("NULL","1","Create new role success", HttpStatus.OK);
        }
        catch (Exception e) {
            return getResponseEntity("NULL","-1","Create new role failed", HttpStatus.BAD_REQUEST);
        }
    }
}
