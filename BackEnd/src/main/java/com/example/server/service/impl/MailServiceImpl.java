package com.example.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.server.dao.ContributionDao;
import com.example.server.dao.UserDao;
import com.example.server.entity.Contribution;
import com.example.server.entity.User;
import com.example.server.service.MailService;
import com.example.server.util.Mail.MailUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    UserDao userDao;

    @Autowired
    ContributionDao contributionDao;

    @Autowired
    MailUtils mailUtils;

    ResourceBundle rb = ResourceBundle.getBundle("email");
    String host_email = rb.getString("SEND_FROM");

    @Override
    public Boolean sendNotifyContributionEmail(Long id) {
        try {
            Contribution contribution = contributionDao.findExistedContributionById(id);
            User mcUser = userDao.findUserManagerByFacultyIdAndRoleId(contribution.getUser().getFaculty().getId(), (long) 3);
            User mmUser = userDao.findUserManagerByFacultyIdAndRoleId(null, (long) 2);
            List<String> ccList = new ArrayList<>();
            ccList.add(mmUser.getEmail());
            String subject = "New commited contribution from student "+
            contribution.getUser().getFullName() + " (code:" + contribution.getUser().getCode() + ")";
            String html = "<p>Student " + contribution.getUser().getFullName()+ " has commited following code "+ contribution.getCode()+ " contribution</p>"+"<p>Please follow url link to see contribution: "+ "http://localhost:3000/contributions/"+id+"</p>";
            try {
                mailUtils.sendAsHtml(host_email, mcUser.getEmail(), ccList, subject, html);
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
}
