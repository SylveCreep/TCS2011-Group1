package com.example.server.service.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.example.server.dao.ContributionDao;
import com.example.server.dao.UserDao;
import com.example.server.entity.Contribution;
import com.example.server.entity.User;
import com.example.server.service.MailService;
import com.example.server.util.Mail.MailUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.server.constant.Constant.*;

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

    @Override
    public Boolean sendResetPasswordMail(User user) {
        try {
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            String key = new String(array, Charset.forName("UTF-8"));
            user.setResetPasswordKey(key);
            user.setKeyCreatedAt(new Date());
            userDao.save(user);

            List<String> ccList = new ArrayList<>();
            String subject = "Reset password for user "+ user.getFullName() + "(Code: " + user.getCode() +")";
            String html= "<p>Reset password link: "
                        + "<a target=\"_blank\" href=\""+urlLink+key+"\">Click this"
                        +"</a>"
                        +"</p>" 
                        +"<p>(Note: Your key will expire 15 minutes since you received this mail)</p>";
            try {
                mailUtils.sendAsHtml(host_email, user.getEmail(), ccList, subject, html);
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
}
