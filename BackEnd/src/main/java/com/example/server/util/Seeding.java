package com.example.server.util;

import java.util.concurrent.ThreadLocalRandom;

import com.example.server.dto.UserDto;
import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.github.javafaker.Faker;
import com.example.server.entity.User;
import com.example.server.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
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
        //seedingUser();
    }

   private void seedingUser(){
       Faker faker = new Faker();
       User nUser = new User();
       Role role = roleDao.findRoleByName("GUEST");
       for (int i = 0; i < 10; i++){
            nUser.setEmail(faker.name().username() + "@gmail.com");
            nUser.setCode( "U" + String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000)));
            nUser.setPassword(bcryptEncoder.encode("123456"));
            nUser.setRole(role);
            nUser.setFaculty(null);
            nUser.setFullName(faker.name().fullName());
            nUser.setAddress(faker.address().buildingNumber());
            userDao.save(nUser);
       }
   }
}