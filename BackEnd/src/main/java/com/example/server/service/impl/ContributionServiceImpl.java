package com.example.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.server.model.response.*;
import com.example.server.service.ContributionService;
import com.example.server.service.FileService;
import com.example.server.util.QueryCheck;
import com.example.server.util.ResponseUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.server.dao.*;
import com.example.server.entity.Contribution;
import com.example.server.entity.Magazine;
import com.example.server.model.request.*;

import static com.example.server.constant.Constant.*;

@Service(value = "contributionService")
public class ContributionServiceImpl implements ContributionService {
    @Autowired
    ContributionDao contributionDao;

    @Autowired
    UserDao userDao;

    @Autowired
    FacultyDao facultyDao;

    @Autowired
    MagazineDao magazineDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private QueryCheck queryCheck;

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
            if(contributionRequest.getCreatedAt() != null){
                hasDate = 1;
            }
            Page<Contribution> list = contributionDao.getContributionList(code, studentName, contributionRequest.getFacultyId(), contributionRequest.getMagazineId(), contributionRequest.getCreatedAt(), hasDate, contributionRequest.getStatus(), PageRequest.of(offset, contributionRequest.getLimit(), sort));
            int lastPage = Math.round(list.getTotalElements() / contributionRequest.getLimit()  + ((list.getTotalElements() % contributionRequest.getLimit() == 0) ? 0 : 1)); 
            List<ContributionResponse> contributionResponse = new ArrayList<>();
            for(Contribution contribution: list){
                ContributionResponse contributionRes = new ContributionResponse();
                contributionRes.setId(contribution.getId());
                contributionRes.setUserId(contribution.getUser().getId());
                contributionRes.setUserName(contribution.getUser().getFullName());
                contributionRes.setCheckedById(contribution.getCheckedBy().getId());
                contributionRes.setCheckedByName(contribution.getCheckedBy().getFullName());
                contributionRes.setPublishedAt(contribution.getPublishedAt());
                contributionRes.setCreatedAt(contribution.getCreated_at());
                contributionRes.setStatus(contribution.getIsApproved());
                contributionRes.setMagazineId(contribution.getMagazine() == null? null:contribution.getMagazine().getId());
                contributionRes.setLinkSource(contribution.getLinkSource());
                contributionRes.setCode(contribution.getCode());
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
        try {
            Contribution contribution = contributionDao.findExistedContributionById(id);
            contribution.setIs_deleted(DELETED);
            try {
                contributionDao.save(contribution);
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean createContribution(ContributionRequest contribution, MultipartFile file) {
        try {
            Contribution nContribution = new Contribution();
            nContribution.setUser(userDao.findByUserId(contribution.getUserId()));
            if(contribution.getCheckedBy() != null){
                nContribution.setCheckedBy(userDao.findByUserId(contribution.getCheckedBy()));
            }
            nContribution.setFaculty(facultyDao.findFacultyById(contribution.getFacultyId()));
            if(contribution.getMagazineId() != null){
                nContribution.setMagazine(magazineDao.findMagazineById(contribution.getMagazineId()));
            }
            nContribution.setCode("C" + String.format("%04d", queryCheck.GetHighestId("contribution")));
            nContribution.setCreated_at(new Date());
            nContribution.setPublishedAt(contribution.getPublishedAt());
            FileResponse fileResponse = fileService.storeContribution(file, nContribution.getCode());
            nContribution.setLinkSource(fileResponse.getPath());
            nContribution.setExtension(fileResponse.getExtension());
            try {
                contributionDao.save(nContribution);
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateContribution(ContributionRequest contribution, MultipartFile file, int withFile) {
        try {
            Contribution uContribution = contributionDao.findExistedContributionById(contribution.getId());
            if(uContribution == null){
                return null;
            }
            if(contribution.getUserId() != null){
                uContribution.setUser(userDao.findByUserId(contribution.getUserId()));
            }
            if(contribution.getCheckedBy() != null){
                uContribution.setCheckedBy(userDao.findByUserId(contribution.getCheckedBy()));
            }
            if(contribution.getFacultyId() != null){
                uContribution.setFaculty(facultyDao.findFacultyById(contribution.getFacultyId()));
            }
            if(contribution.getMagazineId() != null){
                uContribution.setMagazine(magazineDao.findMagazineById(contribution.getMagazineId()));
            }
            if(contribution.getPublishedAt() != null){
                uContribution.setPublishedAt(contribution.getPublishedAt());
            }
            if(contribution.getStatus() != null){
                uContribution.setIsApproved(contribution.getStatus());
            }
            if(withFile == 1){
                FileResponse fileResponse = fileService.storeContribution(file, uContribution.getCode());
                uContribution.setLinkSource(fileResponse.getPath());
                uContribution.setExtension(fileResponse.getExtension());
            }
            try {
                uContribution.setUpdated_at(new Date());
                contributionDao.save(uContribution);
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateStatusContribution(ContributionRequest contribution) {
        try {
            Contribution uContribution = contributionDao.findExistedContributionById(contribution.getId());
            uContribution.setIsApproved(contribution.getStatus());
            try {
                contributionDao.save(uContribution);
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ContributionResponse getContributionById(Long id) {
        try {
            Contribution contribution = contributionDao.findExistedContributionById(id);
            if(contribution == null){
                return null;
            }
            ContributionResponse contributionRes = new ContributionResponse();
            contributionRes.setId(contribution.getId());
            contributionRes.setUserId(contribution.getUser().getId());
            contributionRes.setUserName(contribution.getUser().getFullName());
            contributionRes.setCheckedById(contribution.getCheckedBy().getId());
            contributionRes.setCheckedByName(contribution.getCheckedBy().getFullName());
            contributionRes.setPublishedAt(contribution.getPublishedAt());
            contributionRes.setCreatedAt(contribution.getCreated_at());
            contributionRes.setStatus(contribution.getIsApproved());
            contributionRes.setMagazineId(contribution.getMagazine() == null? null:contribution.getMagazine().getId());
            contributionRes.setLinkSource(contribution.getLinkSource());
            contributionRes.setCode(contribution.getCode());
            contributionRes.setExtension(contribution.getExtension());
            return contributionRes;
        } catch (Exception e) {
            return null;
        }
    }
    
}
