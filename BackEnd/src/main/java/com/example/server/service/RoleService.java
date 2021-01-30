package com.example.server.service;

import com.example.server.entity.Role;

public interface RoleService {
    Role findByName(String name);

    Role findById(int name);
}
