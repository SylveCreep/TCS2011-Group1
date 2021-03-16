package com.example.server.service.impl;

import com.example.server.dao.FacultyDao;
import com.example.server.dao.UserDao;
import com.example.server.entity.Faculty;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.*;
import com.example.server.service.FacultyService;
import com.example.server.util.QueryCheck;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.ResponseUtils.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyDao facultyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private QueryCheck queryCheck;

    @Autowired
    private ResponseUtils responseUtils;

    @Override
    public FacultyPagingResponse searchFaculty(FacultyRequest facultyRequest) {
        try {
            Sort sort = responseUtils.getSortObj(facultyRequest);
            int offset = facultyRequest.getPage() - 1;
            int hasDate = 0;
            Date startDate = null;
            Date endDate = null;
            if(facultyRequest.getStartDate() != null && facultyRequest.getEndDate() != null){
                hasDate = 1;
                startDate = facultyRequest.getStartDate();
                endDate = facultyRequest.getEndDate();
            }
            Page<Faculty> data = facultyDao.searchFaculty(facultyRequest.getCode(), facultyRequest.getFacultyName(), facultyRequest.getManagerName(), startDate, endDate, hasDate, PageRequest.of(offset, facultyRequest.getLimit(), sort));
            int lastPage = Math.round(data.getTotalElements() / facultyRequest.getLimit()  + ((data.getTotalElements() % facultyRequest.getLimit() == 0) ? 0 : 1)); 
            FacultyPagingResponse response = new FacultyPagingResponse();
            List<FacultyResponse> listResponse = new ArrayList<>();
            for(Faculty facl : data){
                FacultyResponse faclRes = new FacultyResponse();
                faclRes.setCode(facl.getCode() == null? "":facl.getCode());
                faclRes.setFacultyId(facl.getId() == null? null:facl.getId());
                faclRes.setFacultyName(facl.getName() == null? "": facl.getName());
                faclRes.setManagerName(facl.getManager() == null? "": facl.getManager().getFullName());
                faclRes.setManagerId(facl.getManager()== null? null: facl.getManager().getId());
                listResponse.add(faclRes);
            }
            response.setFacultyResponses(listResponse);
            response.setLastPage(lastPage);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean update(FacultyRequest facultyRequest) {
        try {
            Optional<Faculty> faclOptional = facultyDao.findById(facultyRequest.getFacultyId());
            Faculty facl = faclOptional.get();
            if(facl.getIs_deleted() == 1){
                return false;
            }
            if(facultyRequest.getManagerId() != null){
                Optional<User> managerOptional = userDao.findById(facultyRequest.getManagerId());
                User manager = managerOptional.get();
                if(manager.getIs_deleted() == 0){
                    facl.setManager(manager);
                } else {
                    return false;
                }
            }
            facl.setName(facultyRequest.getFacultyName());
            facultyDao.save(facl);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Boolean deleted(Long id) {
        try {
            Faculty facl = facultyDao.getOne(id);
            facl.setIs_deleted(1);
            facultyDao.save(facl);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean create(FacultyRequest facultyRequest) {
        try {
            Faculty facl = new Faculty();
            facl.setCode("F" + String.format("%04d", queryCheck.GetHighestId("faculty")));
            facl.setName(facultyRequest.getFacultyName());
            facultyDao.save(facl);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public FacultyResponse getById(Long id) {
        try {
            Optional<Faculty> faclOptional = facultyDao.findById(id);
            Faculty falc = faclOptional.get();
            if(falc != null){
                if(falc.getIs_deleted() == NOTDELETED){
                    FacultyResponse faculResponse = new FacultyResponse();
                    faculResponse.setCode(falc.getCode() == null? "":falc.getCode());
                    faculResponse.setFacultyId(falc.getId());
                    faculResponse.setFacultyName(falc.getName() == null? "": falc.getName());
                    faculResponse.setManagerId(falc.getManager() == null? null: falc.getManager().getId());
                    faculResponse.setManagerName(falc.getManager() == null? "": falc.getManager().getFullName());
                    return faculResponse;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
}
