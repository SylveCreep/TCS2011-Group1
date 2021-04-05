package com.example.server.service;

import com.example.server.entity.User;

public interface MailService {
    Boolean sendNotifyContributionEmail(Long id);

    Boolean sendResetPasswordMail(User user);
}
