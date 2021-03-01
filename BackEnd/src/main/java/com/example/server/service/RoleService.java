package com.example.server.service;

import com.example.server.entity.Role;
import com.example.server.model.request.CreateRole;
import com.example.server.dto.RoleDto;

public interface RoleService {
    Role findByName(String name);

    Role findById(Long id);

    Role saveRole(RoleDto role);

    Role saveRole(CreateRole role);

    RoleDto updateRole(RoleDto user);

    Boolean deleteRole(Long id);
}
