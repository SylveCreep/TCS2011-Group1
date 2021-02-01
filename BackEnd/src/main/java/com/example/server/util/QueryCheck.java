package com.example.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryCheck {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int CheckItemInTable(String tableName){
        try {
            String sql = "SELECT COUNT(*) AS Count FROM `" + tableName + "`";
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            return count;
        }
        catch(EmptyResultDataAccessException e){
            System.err.println(e);
            return 0;
        }
    }

    public int GetHighestId(String tableName){
        try{
            String sql = "SELECT t1.id FROM `"+ tableName +"` t1 INNER JOIN (SELECT id, MAX(id) AS Max FROM `"+ tableName +"`) t2 ON t1.id = t2.Max";
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            return count + 1;
        }
        catch(EmptyResultDataAccessException e){
            System.err.println(e);
            return 1;
        }
    }

    public int CountUserByRole(String roleName){
        try{
            String sql = "SELECT COUNT(u.id) AS NumberOfUser FROM `user` u, `role` r WHERE u.role_id = r.id AND r.name = '"+ roleName +"'";
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            return count;
        }
        catch(EmptyResultDataAccessException e){
            System.err.println(e);
            return 0;
        }
    }
}
