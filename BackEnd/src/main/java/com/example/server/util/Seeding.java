package com.example.server.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Date;

import static com.example.server.constant.Constant.*;

import com.example.server.dto.UserDto;
import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.github.javafaker.Faker;
import com.example.server.entity.User;
import com.example.server.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import antlr.collections.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;

@Component
public class Seeding implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private RoleDao roleDao;

    @Override
    public void run(String... args) throws Exception {
        seedingRole();
        seedingUser();
        
    }



    private void seedingRole(){
        String listRoleName[] = {"Administrator","Marketing Manager", "Marketing Coordinator", "Student", "GUEST"};
        String listRoleCode[] = {"AD", "MM", "MC", "ST", "GU"}; //Rxxx

        for(int i = 0; i < 5; i++){
            Role role = new Role();

            role.setIs_deleted(IntConstant.NOTDELETED);
            role.setName(listRoleName[i]);
            role.setCode(listRoleCode[i]);
            role.setCreated_at(new Date());

            roleDao.save(role);
        }
    }

   private void seedingUser(){
       Faker faker = new Faker();
       Role role = roleDao.findRoleByName("GUEST");
       for (int i = 0; i < 10; i++){
            User nUser = new User();
            nUser.setEmail(faker.name().username() + "@gmail.com");
            nUser.setCode( "U" + String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000)));
            nUser.setPassword(bcryptEncoder.encode("123456"));
            nUser.setRole(role);
            nUser.setFullName(faker.name().fullName());
            nUser.setAddress(faker.address().buildingNumber());
            nUser.setIs_deleted(IntConstant.NOTDELETED);
            nUser.setCreated_at(new Date());;
            userDao.save(nUser);
       }
   }
}