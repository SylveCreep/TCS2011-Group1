package com.example.server.service;

import java.util.List;

import com.example.server.entity.Role;
import com.example.server.model.request.CreateRole;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.request.RoleSearchRequest;
import com.example.server.model.response.RoleLastPageResponse;
import com.example.server.model.response.RoleResponse;
import com.example.server.dto.RoleDto;

public interface RoleService {
    Role findByName(String name);

    Role findById(Long id);

    Role findByCode(String code);

    RoleResponse findByIdToGetRole(Long id);
    
    Role saveRole(RoleDto role);

    Role saveRole(CreateRole role);

    Boolean updateRole(CreateRole user);

    Boolean deleteRole(Long id);

    RoleLastPageResponse searchRoleByName(RoleSearchRequest roleSearchRequest);

    List<Object> getRoleListResponse(PagingRequest pagingRequest);
}
