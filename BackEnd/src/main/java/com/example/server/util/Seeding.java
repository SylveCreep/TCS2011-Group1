package com.example.server.util;

import java.util.Date;

import static com.example.server.constant.Constant.*;

import com.example.server.entity.User;
import com.example.server.entity.Role;
import com.example.server.entity.Faculty;
import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.example.server.dao.FacultyDao;
import com.github.javafaker.Faker;
import com.example.server.util.QueryCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class Seeding implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private QueryCheck queryCheck;

    @Override
    public void run(String... args) throws Exception {
        try {
            seedingRole();
            seedingUserAdmin();
            seedingUserMM();
            seedingUserMC();
            seedingUserStudent();
            seedingUserGUEST();
            System.out.println();
            System.out.println("Success seeding");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void createUser(Faker faker, String pwd, Role role){
        User nUser = new User();
                 nUser.setEmail(faker.name().username() + "@gmail.com");
                 nUser.setCode( "U" + String.format("%04d", queryCheck.GetHighId("user")));
                 nUser.setPassword(bcryptEncoder.encode("admin123"));
                 nUser.setRole(role);
                 nUser.setFullName(faker.name().fullName());
                 nUser.setAddress(faker.address().buildingNumber());
                 nUser.setDateOfBirth(faker.date().birthday(18, 50));
                 nUser.setIs_deleted(IntConstant.NOTDELETED);
                 nUser.setCreated_at(new Date());
 
                 userDao.save(nUser);
    }

    
    private void seedingRole(){
        if (queryCheck.CheckItemInTable("role") < 5){
            String listRoleName[] = {"Administrator","Marketing Manager", "Marketing Coordinator", "Student", "GUEST"};
    
            for(int i = 0; i < 5; i++){
                Role role = new Role();
    
                role.setIs_deleted(IntConstant.NOTDELETED);
                role.setName(listRoleName[i]);
                role.setCode("R" + String.format("%04d", queryCheck.GetHighId("role")));
                role.setCreated_at(new Date());
    
                roleDao.save(role);
            }
        }
        else {
            return;
        }
    }

    private void seedingUserAdmin(){
        if (queryCheck.CountUserByRole("Administrator") < 5){
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
        if (queryCheck.CountUserByRole("Marketing Manager") < 1){
            Faker faker = new Faker();
            Role role = roleDao.findRoleByName("Marketing Manager");
            createUser(faker, "MM1234", role);
        }
        else{
            return;
        }
    }

    private void seedingUserMC(){
        if (queryCheck.CountUserByRole("Marketing Coordinator") < 10){
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
        if (queryCheck.CountUserByRole("Student") < 100){
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
       if (queryCheck.CountUserByRole("GUEST") < 50){
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