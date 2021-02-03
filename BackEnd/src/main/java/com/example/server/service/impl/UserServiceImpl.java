package com.example.server.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.server.dao.UserDao;
import com.example.server.dto.UserDto;
import com.example.server.entity.Role;
import com.example.server.entity.User;
import com.example.server.model.request.CreateAccount;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.request.UserSearchRequest;
import com.example.server.model.response.UserListResponse;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.server.constant.Constant.*;

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
            nUser.setEmail(user.getEmail());
            nUser.setCode("U" + String.format("%04d", queryCheck.GetHighestId("user")));
            nUser.setFullName(user.getFullName());
            nUser.setAddress(user.getAddress());
            nUser.setDateOfBirth(user.getDateOfBirth());
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
    public List<UserListResponse> getUserListResponse(PagingRequest pagingRequest) {
        try {
            Sort sort = responseUtils.getSortObj(pagingRequest);
            List<User> list = userDao.getNonDeletedUser(PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), sort));
            List<UserListResponse> listResponse = new ArrayList<>();
            for(User user: list){
                listResponse.add(new UserListResponse(
                    user.getId(),
                    user.getFullName(),
                    user.getFaculty().getId(),
                    user.getFaculty().getName(),
                    user.getRole().getId(),
                    user.getEmail()
                ));
            }
            return listResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Get users by search
    @Override
    public List<UserListResponse> searchUserByRoleAndFacul(UserSearchRequest userSearchRequest) {
        try {
            Sort sort = responseUtils.getSortObj(userSearchRequest);
            List<User> list = userDao.searchUserByRoleAndFac(userSearchRequest.getUserId(),userSearchRequest.getRoleId(),userSearchRequest.getFacultyId(),PageRequest.of(userSearchRequest.getPage(), userSearchRequest.getLimit(), sort));
            List<UserListResponse> listResponse = new ArrayList<>();
            for(User user: list){
                listResponse.add(new UserListResponse(
                    user.getId(),
                    user.getFullName(),
                    user.getFaculty().getId(),
                    user.getFaculty().getName(),
                    user.getRole().getId(),
                    user.getEmail()
                ));
            }
            return listResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteUser(int id) {
        try {
            User user = userDao.getOne(id);
            user.setIs_deleted(DELETED);
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
            user = modelMapper.map(userDto, User.class);
            userDao.save(user);

            UserDto saveUser = modelMapper.map(user, UserDto.class);
            return saveUser;
        } catch (Exception e) {
           return null;
        }
    }

    
    

}
