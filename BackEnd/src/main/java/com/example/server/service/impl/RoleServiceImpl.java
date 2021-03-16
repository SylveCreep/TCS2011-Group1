package com.example.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.server.constant.Constant;
import com.example.server.dao.RoleDao;
import com.example.server.dto.RoleDto;
import com.example.server.entity.Role;
import com.example.server.model.request.CreateRole;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.request.RoleSearchRequest;
import com.example.server.model.response.RoleLastPageResponse;
import com.example.server.model.response.RoleResponse;
import com.example.server.service.RoleService;
import com.example.server.util.QueryCheck;
import com.example.server.util.ResponseUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.ResponseUtils.*;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleDto roleDto;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private QueryCheck queryCheck;

    @Autowired(required = true)
    private ModelMapper modelMapper;

    @Override
    public RoleResponse findByIdToGetRole(Long id) {
        try{
            Role role = roleDao.getOne(id);
            if(role.getIs_deleted() == Constant.DELETED){
                return null;
            }
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(role.getId());
            roleResponse.setName(role.getName() == null?"":role.getName());
            roleResponse.setCode(role.getCode() == null?"":role.getCode());
            return roleResponse;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Role findById(Long id) {
        Role role = roleDao.findRoleById(id);
        return role;
    }

    @Override
    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }

    @Override
    public Role findByCode(String code){
        Role role = roleDao.findRoleByCode(code);
        return role;
    }

    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        roleDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role saveRole(RoleDto role) {
        Role nRole = role.getRoleFormDto();
        nRole.setCode("R" + String.format("%04d", queryCheck.GetHighestId("role")));
        return roleDao.save(nRole);
    }

    @Override
    public Role saveRole(CreateRole role) {
        try {
            Role nRole = new Role();
            nRole.setCode("R" + String.format("%04d", queryCheck.GetHighestId("role")));
            nRole.setName(role.getName());
            return roleDao.save(nRole);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean updateRole(CreateRole roleDto) {
        try {
            Role role = roleDao.getOne(roleDto.getId());
            role.setName(roleDto.getName());
            role.setCode(roleDto.getCode());
            roleDao.save(role);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteRole(Long id) {
        try {
            Role role = roleDao.getOne(id);
            role.setIs_deleted(Constant.DELETED);
            roleDao.save(role);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public RoleLastPageResponse searchRoleByName(RoleSearchRequest roleSearchRequest){
        try{
            int offset = roleSearchRequest.getPage() - 1;
            Sort sort = responseUtils.getSortObj(roleSearchRequest);
            Page<Role> list = roleDao.searchRoleByName(roleSearchRequest.getRoleId(), roleSearchRequest.getName(), roleSearchRequest.getCode(), PageRequest.of(offset, roleSearchRequest.getLimit(), sort));

            int lastPage = Math.round(list.getTotalElements()/roleSearchRequest.getLimit());
            RoleLastPageResponse object = new RoleLastPageResponse();
            List<RoleResponse> listResponse = new ArrayList<>();
            for(Role role:list){
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setId(role.getId());
                roleResponse.setName(role.getName() == null ?"":role.getName());
                roleResponse.setCode(role.getCode() == null ?"": role.getCode());
                listResponse.add(roleResponse);
            }
            object.setLastPage(lastPage);
            object.setList(listResponse);
            return object;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> getRoleListResponse(PagingRequest pagingRequest) {
        try {
            Sort sort = responseUtils.getSortObj(pagingRequest);
            Page<Role> list = roleDao.getNonDelRole(PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), sort));
            int lastPage = Math.round(list.getTotalElements()/pagingRequest.getLimit());
            List<Object> object = new ArrayList<>();
            List<RoleResponse> listResponse = new ArrayList<>();
            for(Role role: list){
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setId(role.getId());
                roleResponse.setName(role.getName() == null ?"":role.getCode());
                roleResponse.setCode(role.getCode() );
                listResponse.add(roleResponse);
            }
            object.add(listResponse);
            object.add(lastPage);
            return object;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
