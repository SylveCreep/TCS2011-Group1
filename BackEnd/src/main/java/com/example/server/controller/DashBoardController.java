package com.example.server.controller;

import java.util.List;

import com.example.server.constant.Constant;
import com.example.server.model.response.*;
import com.example.server.service.UserService;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DashBoardController {
    @Autowired
    private UserService userService;

    @Autowired
    private ResponseUtils responseUtils;

    @GetMapping(value = "/totalvalues")
    public ResponseEntity<?> getTotalValuesOfReport() {
        try {
            TotalCountResponse totalCountResponse = userService.getTotalCountResponse();
            return responseUtils.getResponseEntity(totalCountResponse, Constant.SUCCESS,
                    "Get total values successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get total values failed",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getTopStudent")
    public ResponseEntity<?> getTopStudent(@Nullable @RequestParam("magazineId") Long magazineId) {
        try {
            List<UserWithMostContributionResponse> userWithMostContributionResponseList = userService
                    .getUserWithMostContribution(magazineId);
            return responseUtils.getResponseEntity(userWithMostContributionResponseList, Constant.SUCCESS,
                    "Get top student successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get top student failed",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
