package com.example.server.util.Mail;

import java.util.List;

import com.example.server.dao.ContributionDao;
import com.example.server.entity.Contribution;
import com.example.server.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.example.server.constant.Constant.*;

@Component
public class MailNotifySystem{

    @Autowired
    ContributionDao contributionDao;

    @Autowired
    MailService mailService;

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void sendNotifyEmailTask() {
        try {
            List<Contribution> notifyContributions = contributionDao.getContributionHasNoComment();
            for(Contribution contribution : notifyContributions){
                Boolean isSuccess = mailService.sendNotifyContributionEmail(contribution.getId(), NEEDNOTIFY, CLIENTURL);
                if(isSuccess == true){
                    contribution.setExpireNotify(SUCCESS);
                    contributionDao.save(contribution);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
