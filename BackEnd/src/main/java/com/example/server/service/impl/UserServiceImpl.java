package com.example.server.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.server.constant.Constant;
import com.example.server.dao.UserDao;
import com.example.server.dto.UserDto;
import com.example.server.entity.Role;
import com.example.server.entity.User;
import com.example.server.model.request.CreateAccount;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.request.UserSearchRequest;
import com.example.server.model.response.UserLastPageResponse;
import com.example.server.model.response.UserListResponse;
import com.example.server.model.response.UserResponse;
import com.example.server.service.RoleService;
import com.example.server.service.UserService;
import com.example.server.util.QueryCheck;
import com.example.server.util.ResponseUtils;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.ResponseUtils.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private QueryCheck queryCheck;

    @Autowired
    private UserDao userDao;

    @Autowired(required = true)
    private ModelMapper modelMapper; 

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        // user.getRoles().forEach(role -> {
        // authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        // });
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String email) {
        return userDao.findByEmail(email);
    }

    // Create account by guest
    @Override
    public User saveGuestRegister(UserDto user) {
        User nUser = user.getUserFromDto();
        if(userDao.findByEmail(user.getEmail()) == null){
            nUser.setEmail(user.getEmail());
        } else {
            return null;
        }
        nUser.setCode("U" + String.format("%04d", queryCheck.GetHighestId("user")));
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("GUEST");
        nUser.setRole(role);
        return userDao.save(nUser);
    }

    // Create account by admin
    @Override
    public User saveRegister(CreateAccount user) {
        try {
            User nUser = new User();
            if(userDao.findByEmail(user.getEmail()) == null){
                nUser.setEmail(user.getEmail());
            } else {
                return null;
            }
            nUser.setCode("U" + String.format("%04d", queryCheck.GetHighestId("user")));
            nUser.setFullName(user.getFullName());
            nUser.setAddress(user.getAddress());
            nUser.setDateOfBirth(user.getDateOfBirth());
            nUser.setPhoneNumber(user.getPhoneNumber());
            nUser.setGender(user.getGender());
            nUser.setAvatar(user.getAvatar());
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            Role role = roleService.findById(user.getRoleId());
            nUser.setRole(role);
            return userDao.save(nUser);
        } catch (Exception e) {
            return null;
        }
    }

    //Get non deleted users
    @Override
    public List<Object> getUserListResponse(PagingRequest pagingRequest) {
        try {
            Sort sort = responseUtils.getSortObj(pagingRequest);
            Page<User> list = userDao.getNonDeletedUser(PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), sort));
            int lastPage = Math.round(list.getTotalElements()/pagingRequest.getLimit());
            List<Object> object = new ArrayList<>();
            List<UserResponse> listResponse = new ArrayList<>();
            for(User user: list){
                UserResponse userRes = new UserResponse();
                userRes.setId(user.getId());
                userRes.setRoleId(user.getRole() == null?null:user.getRole().getId());
                userRes.setFacultyId(user.getFaculty() == null?null:user.getFaculty().getId());
                userRes.setCode(user.getCode() == null?"":user.getCode());
                userRes.setFullName(user.getFullName() == null?"":user.getFullName());
                userRes.setRoleName(user.getRole() == null?"":user.getRole().getName());
                userRes.setFacultyName(user.getFaculty() == null?"":user.getFaculty().getName());
                userRes.setEmail(user.getEmail()==null?"":user.getEmail());
                userRes.setAddress(user.getAddress()==null?"":user.getAddress());
                userRes.setPhoneNumber(user.getPhoneNumber()==null?null:user.getPhoneNumber());
                userRes.setDateOfBirth(dateFormat(user.getDateOfBirth()));
                listResponse.add(userRes);
            }
            object.add(listResponse);
            object.add(lastPage);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Get users by search
    @Override
    public UserLastPageResponse searchUserByRoleAndFacul(UserSearchRequest userSearchRequest) {
        try {
            int offset = userSearchRequest.getPage() -1;
            Sort sort = responseUtils.getSortObj(userSearchRequest);
            int hasDate = 0;
            if(userSearchRequest.getStartDate() != null && userSearchRequest.getEndDate() != null){
                hasDate = 1;
            }
            Page<User> list = userDao.searchUserByRoleAndFac(userSearchRequest.getUserId(),userSearchRequest.getRoleId(),userSearchRequest.getFacultyId(),
            userSearchRequest.getFullName(),userSearchRequest.getRoleName(),userSearchRequest.getFacultyName(),
            userSearchRequest.getEmail(),userSearchRequest.getStartDate(),userSearchRequest.getEndDate(),hasDate,userSearchRequest.getGender(),
            PageRequest.of(offset, userSearchRequest.getLimit(), sort));

            int lastPage = Math.round(list.getTotalElements()/userSearchRequest.getLimit());
            UserLastPageResponse object = new UserLastPageResponse();
            List<UserResponse> listResponse = new ArrayList<>();
            for(User user: list){
                UserResponse userRes = new UserResponse();
                userRes.setId(user.getId());
                userRes.setRoleId(user.getRole() == null?null:user.getRole().getId());
                userRes.setFacultyId(user.getFaculty() == null?null:user.getFaculty().getId());
                userRes.setCode(user.getCode() == null?"":user.getCode());
                userRes.setFullName(user.getFullName() == null?"":user.getFullName());
                userRes.setRoleName(user.getRole() == null?"":user.getRole().getName());
                userRes.setFacultyName(user.getFaculty() == null?"":user.getFaculty().getName());
                userRes.setEmail(user.getEmail()==null?"":user.getEmail());
                userRes.setAddress(user.getAddress()==null?"":user.getAddress());
                userRes.setPhoneNumber(user.getPhoneNumber()==null?null:user.getPhoneNumber());
                userRes.setDateOfBirth(dateFormat(user.getDateOfBirth()));
                listResponse.add(userRes);
            }
            object.setLastPage(lastPage);
            object.setList(listResponse);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteUser(Long id) {
        try {
            User user = userDao.getOne(id);
            user.setIs_deleted(Constant.DELETED);
            userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDto update(UserDto userDto){
        try {
            User user = userDao.getOne(userDto.getId());
            if(userDao.findByEmail(userDto.getEmail()) != null){
                return null;
            }
            user.setEmail(userDto.getEmail());
            user.setFullName(userDto.getFullName());
            user.setAddress(userDto.getAddress());
            user.setDateOfBirth(userDto.getDateOfBirth());
            userDao.save(user);

            UserDto saveUser = modelMapper.map(user, UserDto.class);
            return saveUser;
        } catch (Exception e) {
           return null;
        }
    }

    @Override
    public UserResponse findById(Long id) {
        try {
            User user = userDao.getOne(id);
            if(user.getIs_deleted() == DELETED){
                return null;
            }
            UserResponse userRes = new UserResponse();
            userRes.setId(user.getId());
            userRes.setRoleId(user.getRole() == null?null:user.getRole().getId());
            userRes.setFacultyId(user.getFaculty() == null?null:user.getFaculty().getId());
            userRes.setCode(user.getCode() == null?"":user.getCode());
            userRes.setFullName(user.getFullName() == null?"":user.getFullName());
            userRes.setRoleName(user.getRole() == null?"":user.getRole().getName());
            userRes.setFacultyName(user.getFaculty() == null?"":user.getFaculty().getName());
            userRes.setEmail(user.getEmail()==null?"":user.getEmail());
            userRes.setAddress(user.getAddress()==null?"":user.getAddress());
            userRes.setPhoneNumber(user.getPhoneNumber()==null?null:user.getPhoneNumber());
            userRes.setDateOfBirth(dateFormat(user.getDateOfBirth()));
            return userRes;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserResponse> getUserNotIsManager() {
        try {
            List<User> users = userDao.searchUserNotIsManager();
            List<UserResponse> list = new ArrayList<>();
            for(User user: users){
                UserResponse userRes = new UserResponse();
                userRes.setId(user.getId());
                userRes.setRoleId(user.getRole() == null?null:user.getRole().getId());
                userRes.setFacultyId(user.getFaculty() == null?null:user.getFaculty().getId());
                userRes.setCode(user.getCode() == null?"":user.getCode());
                userRes.setFullName(user.getFullName() == null?"":user.getFullName());
                userRes.setRoleName(user.getRole() == null?"":user.getRole().getName());
                userRes.setFacultyName(user.getFaculty() == null?"":user.getFaculty().getName());
                userRes.setEmail(user.getEmail()==null?"":user.getEmail());
                userRes.setAddress(user.getAddress()==null?"":user.getAddress());
                userRes.setPhoneNumber(user.getPhoneNumber()==null?null:user.getPhoneNumber());
                userRes.setDateOfBirth(dateFormat(user.getDateOfBirth()));
                list.add(userRes);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    
    

}
