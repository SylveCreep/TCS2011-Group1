package com.example.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import jdk.internal.org.jline.utils.Log;

public class QueryChecking{

    @Autowired
    JdbcTemplate jdbcTemplate;



    public int CheckNumberOfUserByRole(String roleName){
        String sql = "SELECT COUNT(u.id) AS NumberOfUser FROM `user` u, `role` r WHERE u.role_id = r.id AND r.name = '" + roleName + "'";
        int numberOfUser = jdbcTemplate.queryForObject(sql, Integer.class);
        return numberOfUser;
    }
    
    public int CheckItemInTable(){
        try{
            String sql = String.valueOf("SELECT COUNT(*) AS Count FROM `role`");
            return jdbcTemplate.queryForObject(sql, Integer.class);
        }
       catch(EmptyResultDataAccessException e){
            System.out.println(e);
            return 0;
       }
    }
    
    public int CheckHighestIdUser(String nameTable){
        String sql = "SELECT COUNT(t.id) FROM `" + nameTable+ "` t";
        if (jdbcTemplate.queryForObject(sql, Integer.class) == null){
            return 1;
        }
        else {
            int highestId = jdbcTemplate.queryForObject(sql, Integer.class);
            return highestId + 1;
        }
    }
    
    public int SelectRandomId (String nameTable){
        String sql = "SELECT id FROM `" + nameTable +"` ORDER BY RAND() LIMIT 1";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count > 0){
            int randomId = jdbcTemplate.queryForObject(sql, Integer.class);
            return randomId;
        }
        else{
            return 0;   
        }
    }
}
