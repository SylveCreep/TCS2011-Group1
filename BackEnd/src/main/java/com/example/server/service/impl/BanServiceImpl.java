package com.example.server.service.impl;

import com.example.server.dao.BanDao;
import com.example.server.entity.BanList;
import com.example.server.service.BanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanServiceImpl implements BanService {

    @Autowired
    private BanDao banDao;

    @Override
    public String add(String token) {
        try {
            BanList existed = banDao.findByToken(token);
            if(existed != null){
                return "Token already existed";
            }
            BanList object = new BanList();
            object.setToken(token);
            banDao.save(object);
            return "Log out successfully";
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String delete(String token) {
        try {
            BanList object = banDao.findByToken(token);
            if(object != null){
                banDao.delete(object);
                return "Log out success";
            } else {
                return "Token not existed";
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BanList find(String token) {
        BanList object = banDao.findByToken(token);
        return object;
    }
    
}
