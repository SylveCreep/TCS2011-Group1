package com.example.server.service;

import com.example.server.entity.User;

public interface MailService {
    Boolean sendNotifyContributionEmail(Long id, int type, String url);

    Boolean sendResetPasswordMail(User user);
}
