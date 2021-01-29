package com.example.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class QueryChecking{

    @Autowired
    JdbcTemplate jdbcTemplate;



    public int CheckNumberOfUserByRole(String roleName){
        String sql = "SELECT COUNT(u.id) AS NumberOfUser FROM `user` u, `role` r WHERE u.role_id = r.id AND r.name = '" + roleName + "'";
        int numberOfUser = jdbcTemplate.queryForObject(sql, Integer.class);
        return numberOfUser;
    }
    
    public int CheckItemInTable(String table){
        String sql = "SELECT COUNT(id) AS NumberOfRole FROM `" + table + "`";
        int numberOfItem = jdbcTemplate.queryForObject(sql, Integer.class);
        return numberOfItem;
    }
    
    public int CheckHighestIdUser(String nameTable){
        String sql = "SELECT COUNT(id) FROM `" + nameTable+ "`";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count > 0){
            int highestId = jdbcTemplate.queryForObject(sql, Integer.class);
            return highestId + 1;
        }
        else {
            return 1;
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
