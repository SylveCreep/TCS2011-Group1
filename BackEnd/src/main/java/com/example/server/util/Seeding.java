package com.example.server.util;

import java.util.Date;

import static com.example.server.constant.Constant.*;

import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.github.javafaker.Faker;
import com.example.server.entity.User;
import com.example.server.entity.Role;
import com.example.server.util.QueryChecking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;


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

    private QueryChecking queryChecking = new QueryChecking();

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
                 nUser.setCode( "U" + String.format("%04d", queryChecking.CheckHighestIdUser("user")));
                 nUser.setPassword(bcryptEncoder.encode("admin123"));
                 nUser.setRole(role);
                 nUser.setFullName(faker.name().fullName());
                 nUser.setAddress(faker.address().buildingNumber());
                 nUser.setIs_deleted(IntConstant.NOTDELETED);
                 nUser.setCreated_at(new Date());
 
                 userDao.save(nUser);
    }

    
    private void seedingRole(){
        if (queryChecking.CheckItemInTable("role") < 5){
            String listRoleName[] = {"Administrator","Marketing Manager", "Marketing Coordinator", "Student", "GUEST"};
    
            for(int i = 0; i < 5; i++){
                Role role = new Role();
    
                role.setIs_deleted(IntConstant.NOTDELETED);
                role.setName(listRoleName[i]);
                role.setCode("R" + String.format("%04d", queryChecking.CheckHighestIdUser("role")));
                role.setCreated_at(new Date());
    
                roleDao.save(role);
            }
        }
        else {
            return;
        }
    }

    private void seedingUserAdmin(){
        if (queryChecking.CheckNumberOfUserByRole("Administrator") < 5){
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
        if (queryChecking.CheckNumberOfUserByRole("Marketing Manager") < 1){
            Faker faker = new Faker();
            Role role = roleDao.findRoleByName("Marketing Manager");
            createUser(faker, "MM1234", role);
        }
        else{
            return;
        }
    }

    private void seedingUserMC(){
        if (queryChecking.CheckNumberOfUserByRole("Marketing Coordinator") < 10){
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
        if (queryChecking.CheckNumberOfUserByRole("Student") < 100){
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
       if (queryChecking.CheckNumberOfUserByRole("GUEST") < 50){
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