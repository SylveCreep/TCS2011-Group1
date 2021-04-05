package com.example.server.controller;

import com.example.server.util.ResponseUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.server.constant.Constant.*;

import com.example.server.dao.ContributionDao;
import com.example.server.dao.UserDao;
import com.example.server.entity.Contribution;
import com.example.server.entity.User;
import com.example.server.model.response.ContributionResponse;
import com.example.server.service.ContributionService;
import com.example.server.service.MailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private MailService mailService;

    @Autowired
    private ContributionDao contributionDao;

    @Autowired
    private UserDao userDao;
    
    @PreAuthorize("hasRole('R0002') or hasRole('R0003') ")
    @GetMapping(value = "/contribution", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> notifyContributionAfterCreated(@RequestParam(value = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Must has contribution id",
                        HttpStatus.BAD_REQUEST);
            }
            Contribution contribution = contributionDao.findExistedContributionById(id);
            if(contribution == null){
                return responseUtils.getResponseEntity("NULL", FAILURE, "Contribution not existed",
                        HttpStatus.BAD_REQUEST);
            }
            if(contribution.getIsApproved() != PENDING){
                return responseUtils.getResponseEntity("NULL", FAILURE, "Contribution must in pending status",
                        HttpStatus.BAD_REQUEST);
            }
            Boolean sendStatus = mailService.sendNotifyContributionEmail(id);
            if (sendStatus == false) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Send mail fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Send mail successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Send mail fail",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/forgotpassword", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> forgotPasswordMail(@RequestParam(value = "email") String email) {
        try {
            if (email == null || StringUtils.isBlank(email) || !email.contains("@")) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Email invalid",
                        HttpStatus.BAD_REQUEST);
            }
            User user = userDao.findExistedUserByEmail(email);
            if(user == null){
                return responseUtils.getResponseEntity("NULL", FAILURE, "Account not existed",
                        HttpStatus.BAD_REQUEST);
            }
            Boolean sendStatus = mailService.sendResetPasswordMail(user);
            if (sendStatus == false) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Send mail fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Send mail successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Send mail fail",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
