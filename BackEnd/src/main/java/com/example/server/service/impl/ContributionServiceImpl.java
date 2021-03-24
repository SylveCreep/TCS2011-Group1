package com.example.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.server.model.response.*;
import com.example.server.service.ContributionService;
import com.example.server.util.ResponseUtils;

import java.util.ArrayList;
import java.util.List;

import com.example.server.dao.ContributionDao;
import com.example.server.model.request.*;

@Service(value = "contributionService")
public class ContributionServiceImpl implements ContributionService {
    @Autowired
    ContributionDao contributionDao;

    @Autowired
    private ResponseUtils responseUtils;

    @Override
    public ContributionPagingResponse getContributionList(ContributionRequest contributionRequest) {
        try {
            Sort sort = responseUtils.getSortObj(contributionRequest);
            int offset = contributionRequest.getPage() - 1;
            String code = contributionRequest.getCode();
            String studentName = contributionRequest.getStudentName();
            int hasDate = 0;
            if(code.isEmpty()){
                code = null;
            }
            if(studentName.isEmpty()){
                studentName = null;
            }
            if(contributionRequest.getSubmitDate() != null){
                hasDate = 1;
            }
            Page<ContributionResponse> list = contributionDao.getContributionList(code, studentName, contributionRequest.getFacultyId(), contributionRequest.getMagazineId(), contributionRequest.getSubmitDate(), hasDate, contributionRequest.getStatus(), PageRequest.of(offset, contributionRequest.getLimit(), sort));
            int lastPage = Math.round(list.getTotalElements() / contributionRequest.getLimit()  + ((list.getTotalElements() % contributionRequest.getLimit() == 0) ? 0 : 1)); 
            List<ContributionResponse> contributionResponse = new ArrayList<>();
            for(ContributionResponse contribution: list){
                ContributionResponse contributionRes = new ContributionResponse();
                contributionRes.setId(contribution.getId());
                contributionRes.setUserId(contribution.getUserId());
                contributionRes.setUserName(contribution.getUserName());
                contributionRes.setCheckedById(contribution.getCheckedById());
                contributionRes.setCheckedByName(contribution.getCheckedByName());
                contributionRes.setPublishedAt(contribution.getPublishedAt());
                contributionRes.setCreatedAt(contribution.getCreatedAt());
                contributionRes.setStatus(contribution.getStatus());
                contributionRes.setMagazineId(contribution.getMagazineId());
                contributionRes.setLinkSource(contribution.getLinkSource());
                contributionResponse.add(contributionRes);
            }
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
