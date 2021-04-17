package com.example.server.util;

import java.lang.StackWalker.Option;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.example.server.constant.Constant.*;

import com.example.server.entity.User;
import com.example.server.entity.Role;
import com.example.server.entity.Faculty;
import com.example.server.entity.Magazine;
import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.example.server.constant.Constant;
import com.example.server.dao.FacultyDao;
import com.example.server.dao.MagazineDao;
import com.github.javafaker.Faker;
import com.rabbitmq.client.Return;
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
    private FacultyDao facultyDao;

    @Autowired
    private MagazineDao magazineDao;

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
            seedingUserGUEST();
            seedingUserMC();
            seedingFaculty();
            seedingUserStudent();
            seedingMagazine();
            System.out.println();
            System.out.println("Success seeding");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void createUser(Faker faker, String pwd, Role role){
        Random random = new Random();
        User nUser = new User();
                 nUser.setEmail(faker.name().username() + "@gmail.com");
                 nUser.setCode( "U" + String.format("%04d", queryCheck.GetHighestId("user")));
                 nUser.setPassword(bcryptEncoder.encode(pwd));
                 nUser.setRole(role);
                 nUser.setFullName(faker.name().fullName());
                 nUser.setAddress(faker.address().buildingNumber());
                 nUser.setDateOfBirth(faker.date().birthday(18, 50));
                 nUser.setIs_deleted(Constant.NOTDELETED);
                 nUser.setCreated_at(new Date());
                 nUser.setGender(Constant.MALE);
                 nUser.setAvatar("avatar-l400.jpg");
                 nUser.setFaculty(facultyDao.getOne(Long.valueOf(1 + (int)(Math.random() * (queryCheck.GetHighestId("faculty") - 1)))));
                 String phoneString = faker.phoneNumber().cellPhone();
                 Long phoneNumber = Long.parseLong(phoneString.replaceAll("[\\D+]", "").trim());
                 nUser.setPhoneNumber(phoneNumber);
                 userDao.save(nUser);
    }

    private void createUserSpec(Faker faker, String email, String pwd, Role role){
        Random random = new Random();
        User nUser = new User();
                 nUser.setEmail(email + "@gmail.com");
                 nUser.setCode( "U" + String.format("%04d", queryCheck.GetHighestId("user")));
                 nUser.setPassword(bcryptEncoder.encode(pwd));
                 nUser.setRole(role);
                 nUser.setFullName(faker.name().fullName());
                 nUser.setAddress(faker.address().buildingNumber());
                 nUser.setDateOfBirth(faker.date().birthday(18, 50));
                 nUser.setIs_deleted(Constant.NOTDELETED);
                 nUser.setCreated_at(new Date());
                 nUser.setGender(Constant.MALE);
                 nUser.setAvatar("avatar-l400.jpg");
                 nUser.setFaculty(facultyDao.getOne(Long.valueOf(1 + (int)(Math.random() * (queryCheck.GetHighestId("faculty") - 1)))));
                 String phoneString = faker.phoneNumber().cellPhone();
                 Long phoneNumber = Long.parseLong(phoneString.replaceAll("[\\D+]", "").trim());
                 nUser.setPhoneNumber(phoneNumber);
                 userDao.save(nUser);
    }

    
    private void seedingRole(){
        if (queryCheck.CheckItemInTable("role") < 5){
            String listRoleName[] = {"ADMIN","Marketing Manager", "Marketing Coordinator", "Student", "GUEST"};
    
            for(int i = 0; i < 5; i++){
                Role role = new Role();
    
                role.setIs_deleted(Constant.NOTDELETED);
                role.setName(listRoleName[i]);
                role.setCode("R" + String.format("%04d", queryCheck.GetHighestId("role")));
                role.setCreated_at(new Date());
    
                roleDao.save(role);
            }
        }
        else {
            return;
        }
    }

    private void seedingUserAdmin(){
        if (queryCheck.CountUserByRole("ADMIN") < 5){
             Faker faker = new Faker();
             Role role = roleDao.findRoleByName("ADMIN");
             for (int i = 0; i < 5; i++){
                 createUser(faker, "admin123", role);
             }
             createUserSpec(faker, "admin", "123456a", role);

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
            createUserSpec(faker, "mm", "123456a", role);
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
             createUserSpec(faker, "mc", "123456a", role);
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
            createUserSpec(faker, "student", "123456a", role);
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
                createUser(faker, "GUEST123", role);
            }
       }
       else{
           return;
       }
   }


   ///Faculty
   private Boolean createFaculty(Faker faker, Long userId){
       //Optional<User> managerList = userDao.findById(userId);
       //User manager = managerList.get();
       Faculty nFaculty = new Faculty();
            nFaculty.setCode("F" + String.format("%04d", queryCheck.GetHighestId("faculty")));
            nFaculty.setName(faker.educator().course());

            facultyDao.save(nFaculty);
            return true;
   }

   private void seedingFaculty(){
       if (queryCheck.CheckItemInTable("faculty") < 5){
           Random random = new Random();
           Faker faker = new Faker();
           List<Long> MCList = userDao.searchMC();
           int countSeedingFaculty = 0;
           while(countSeedingFaculty < 5){

               if(createFaculty(faker, Long.valueOf(MCList.get(random.nextInt(MCList.size())))) == true);
                    countSeedingFaculty++;
           }
       }
   }

   public Date getDateIncrease(Date curr, int DateIncrease){
       //Date curr = new Date();
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(curr);
       calendar.add(Calendar.DATE, DateIncrease);
        return calendar.getTime();
   }

   public Boolean createMagazine(Faker faker, int idFaculty, int idMagazineInc){
       try{
            Date currDate = faker.date().between(getDateIncrease(new Date(), -90), getDateIncrease(new Date(), 90));
            Magazine magazine = new Magazine();
                magazine.setCode("M" + String.format("%04d", queryCheck.GetHighestId("magazine")));
                magazine.setTheme(facultyDao.getNonDelFaculty().get(idFaculty).getName() + ": " + idMagazineInc);
                magazine.setCreated_at(currDate);
                magazine.setFinished_at(getDateIncrease(currDate, 10));
                magazine.setPublished_at(getDateIncrease(currDate, 20));
                Date farDate = new SimpleDateFormat("yyyy-MM-dd").parse("2500-12-31");
                magazine.setClose_at(farDate);
            magazineDao.save(magazine);
            return true;
       } catch(Exception e){
           return false;
       }
   }

   public void seedingMagazine(){
       if (queryCheck.CheckItemInTable("magazine") == 0){
            Faker faker = new Faker();
            int sizeFaculty = queryCheck.CheckItemInTable("faculty");
            for (int i = 0; i < sizeFaculty; i++){
                int y = 1;
                int yDeny = 0;
                while (y < 6 || yDeny == 10){
                    if (createMagazine(faker, i, y))
                        y++;
                    else{
                        yDeny++;
                    }
                }
            }
       }
   }
}