package com.example.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.server.dao.RoleDao;
import com.example.server.dto.RoleDto;
import com.example.server.entity.Role;
import com.example.server.model.request.CreateRole;
import com.example.server.service.RoleService;
import com.example.server.util.QueryCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private QueryCheck queryCheck;

    @Override
    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }

    @Override
    public Role findById(int id) {
        Role role = roleDao.findRoleById(id);
        return role;
    }

    public List<Role> findAll(){
        List<Role> list = new ArrayList<>();
        roleDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role saveRole(RoleDto role){
        Role nRole = role.getRoleFormDto();
        nRole.setCode("R" + String.format("%04d", queryCheck.GetHighestId("role")));
        return roleDao.save(nRole);
    }

    @Override
    public Role saveRole(CreateRole role){
        try{
            Role nRole = new Role();
            nRole.setCode("R" + String.format("%04d", queryCheck.GetHighestId("role")));
            nRole.setName(role.getName());
            return roleDao.save(nRole);
        }
        catch(Exception e){
            return null;
        }
    }
}
