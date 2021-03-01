package com.example.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.server.constant.Constant;
import com.example.server.dao.RoleDao;
import com.example.server.dto.RoleDto;
import com.example.server.entity.Role;
import com.example.server.model.request.CreateRole;
import com.example.server.service.RoleService;
import com.example.server.util.QueryCheck;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleDto roleDto;

    @Autowired
    private QueryCheck queryCheck;

    @Autowired(required = true)
    private ModelMapper modelMapper;

    @Override
    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }

    @Override
    public Role findById(Long id) {
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

    @Override
    public RoleDto updateRole(RoleDto roleDto) {
        try{
            Role role = roleDao.getOne(roleDto.getId());
            role = modelMapper.map(roleDto, Role.class);
            roleDao.save(role);
            RoleDto saveRole = modelMapper.map(role, RoleDto.class);
            return saveRole;
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Boolean deleteRole(Long id) {
        try{
            Role role = roleDao.findRoleById(id);
            role.setIs_deleted(Constant.DELETED);
            roleDao.save(role);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
