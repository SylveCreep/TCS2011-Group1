package com.example.server.service.impl;

import com.example.server.dao.UserDao;
import com.example.server.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    UserDao userDao;

    @Override
    public Boolean sendNotifyContributionEmail(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
