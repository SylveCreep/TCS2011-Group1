package com.example.server.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.LogManager;
import java.sql.ResultSet;
import java.util.Date;

import static com.example.server.constant.Constant.*;

import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.github.javafaker.Faker;
import com.example.server.entity.User;
import com.example.server.entity.Role;

import org.hibernate.annotations.common.util.impl.Log_$logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.logging.log4j.Logger;

import antlr.collections.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class Seeding implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        seedingRole();
        seedingUserAdmin();
        seedingUserMM();
        seedingUserMC();
        seedingUserGUEST();
        seedingUserStudent();
        
    }

    private void createUser(Faker faker, String pwd, Role role){
        User nUser = new User();
                 nUser.setEmail(faker.name().username() + "@gmail.com");
                 nUser.setCode( "U" + String.format("%04d", CheckHighestIdUser("user")));
                 nUser.setPassword(bcryptEncoder.encode("admin123"));
                 nUser.setRole(role);
                 nUser.setFullName(faker.name().fullName());
                 nUser.setAddress(faker.address().buildingNumber());
                 nUser.setIs_deleted(IntConstant.NOTDELETED);
                 nUser.setCreated_at(new Date());
 
                 userDao.save(nUser);
    }

    private int CheckNumberOfUserByRole(String roleName){
        String sql = "SELECT COUNT(u.id) AS NumberOfUser FROM `user` u, `role` r WHERE u.role_id = r.id AND r.name = '" + roleName + "'";
        int numberOfUser = jdbcTemplate.queryForObject(sql, Integer.class);
        return numberOfUser;
    }

    private int CheckHighestIdUser(String nameTable){
        String sql = "SELECT COUNT(id) FROM `" + nameTable+ "`";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count > 0){
            sql = "SELECT MAX(id) FROM `" + nameTable +"`";
            int highestId = jdbcTemplate.queryForObject(sql, Integer.class);
            return highestId + 1;
        }
        else {
            return 1;
        }
    }

    private void seedingRole(){
        String sql = "SELECT COUNT(id) AS NumberOfRole FROM `role`";
        int numberOfRole = jdbcTemplate.queryForObject(sql, Integer.class);

        if (numberOfRole < 5){
            String listRoleName[] = {"Administrator","Marketing Manager", "Marketing Coordinator", "Student", "GUEST"};
    
            for(int i = 0; i < 5; i++){
                Role role = new Role();
    
                role.setIs_deleted(IntConstant.NOTDELETED);
                role.setName(listRoleName[i]);
                role.setCode("R" + String.format("%04d", CheckHighestIdUser("role")));
                role.setCreated_at(new Date());
    
                roleDao.save(role);
            }
        }
        else {
            return;
        }
    }

    private void seedingUserAdmin(){
        int numberOfUser = CheckNumberOfUserByRole("Administrator");
 
        if (numberOfUser < 5){
             Faker faker = new Faker();
             Role role = roleDao.findRoleByName("Administrator");
             for (int i = 0; i < 5; i++){
                 createUser(faker, "admin123", role);
             }
        }
        else{
            return;
        }
        
    }

    private void seedingUserMM(){
        int numberOfUser = CheckNumberOfUserByRole("Marketing Manager");
 
        if (numberOfUser < 1){
            Faker faker = new Faker();
            Role role = roleDao.findRoleByName("Marketing Manager");
            createUser(faker, "MM1234", role);
        }
        else{
            return;
        }
        
    }

    private void seedingUserMC(){
        int numberOfUser = CheckNumberOfUserByRole("Marketing Coordinator");
 
        if (numberOfUser < 10){
             Faker faker = new Faker();
             Role role = roleDao.findRoleByName("Marketing Coordinator");
             for (int i = 0; i < 10; i++){
                createUser(faker, "MC1234", role);
             }
        }
        else{
            return;
        }
        
    }

    private void seedingUserStudent(){
        int numberOfUser = CheckNumberOfUserByRole("Student");
 
        if (numberOfUser < 100){
            Faker faker = new Faker();
            Role role = roleDao.findRoleByName("Student");
            for (int i = 0; i < 100; i++){
                createUser(faker, "Student123", role);
            }
        }
        else{
            return;
        }
        
    }

   private void seedingUserGUEST(){
       int numberOfUser = CheckNumberOfUserByRole("GUEST");

       if (numberOfUser < 50){
            Faker faker = new Faker();
            Role role = roleDao.findRoleByName("GUEST");
            for (int i = 0; i < 100; i++){
                createUser(faker, "123456", role);
            }
       }
       else{
           return;
       }
       
   }
}