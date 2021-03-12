package com.example.server.controller;

import javax.validation.Valid;

import com.example.server.entity.Role;
import com.example.server.model.request.CreateRole;
import com.example.server.model.request.RoleSearchRequest;
import com.example.server.model.response.RoleLastPageResponse;
import com.example.server.model.response.RoleResponse;
import com.example.server.service.RoleService;
import com.example.server.util.ResponseUtils;
import com.example.server.constant.Constant;
import com.example.server.dto.RoleDto;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private ResponseUtils responseUtils;

    @GetMapping("/findRoleByName")
    public Role findRoleByName (@RequestParam("name") String name){
        return roleService.findByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create"/*, consumes = {"text/plain", "application/*"}, produces = "application/json"*/)
    public ResponseEntity<?> createRole(@Valid @RequestBody CreateRole role){
        try{
            roleService.saveRole(role);
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Create role successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Create role fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", consumes = {"test/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> deleteRole(@PathVariable(name="id") Long id){
        try{
            if(id == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has role id", HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = roleService.deleteRole(id);
            if(is_deleted == false){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Delete role fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Delete role successfully", HttpStatus.OK);
        }
        catch(Exception e){
            return responseUtils.getResponseEntity("Null", Constant.FAILURE, "Delete role fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = {"test/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> updateRole(@RequestBody RoleDto roleDto) {
        try{
            if (roleDto.getId() == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has role id", HttpStatus.BAD_REQUEST);
            }
            RoleDto role = roleService.updateRole(roleDto);
            if(role == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update role fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(role, Constant.SUCCESS, "Update role successfully", HttpStatus.OK);
        }
        catch(Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update role fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/filter")
    public ResponseEntity<?> showRoleBySearch(@RequestBody RoleSearchRequest roleSearchRequest){
        try{
            if (roleSearchRequest.getLimit() < 0 || roleSearchRequest.getPage() < 0){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            RoleLastPageResponse roles = roleService.searchRoleByName(roleSearchRequest);
            if (roles == null){
                return responseUtils.getResponseEntity(roles, Constant.SUCCESS, "Don't have role", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(roles.getList(), Constant.SUCCESS, "Show role success", roles.getLastPage(), HttpStatus.OK);
        } catch(Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Show role failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> getRole(@PathVariable(name = "id") Long id){
        try{
            if(id == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has role id", HttpStatus.BAD_REQUEST);
            }
            RoleResponse roleResponse = roleService.findByIdToGetRole(id);
            if (roleResponse == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get role fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(roleResponse, Constant.SUCCESS, "Get role successfully", HttpStatus.OK);
        } catch (Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get role fail", HttpStatus.BAD_REQUEST);
        }
    }
}
