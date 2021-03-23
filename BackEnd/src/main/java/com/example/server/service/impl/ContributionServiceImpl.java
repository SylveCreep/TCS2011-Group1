package com.example.server.service.impl;

import org.springframework.stereotype.Service;

import com.example.server.model.response.*;
import com.example.server.service.ContributionService;

import java.util.ArrayList;
import java.util.List;

import com.example.server.model.request.*;

@Service(value = "contributionService")
public class ContributionServiceImpl implements ContributionService {

    @Override
    public ContributionPagingResponse getContributionList(ContributionRequest contributionRequest) {
        try {
            int lastPage = 0;
            List<ContributionResponse> contributionResponse = new ArrayList<>();
            ContributionPagingResponse response = new ContributionPagingResponse();
            response.setList(contributionResponse);
            response.setLastPage(lastPage);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleted(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
