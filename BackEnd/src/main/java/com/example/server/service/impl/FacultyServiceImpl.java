package com.example.server.service.impl;

import com.example.server.dao.FacultyDao;
import com.example.server.dao.UserDao;
import com.example.server.entity.Faculty;
import com.example.server.entity.User;
import com.example.server.model.request.*;
import com.example.server.model.response.*;
import com.example.server.service.FacultyService;
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

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyDao facultyDao;

    @Autowired
    private UserDao userDao;

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
            int lastPage = Math.round(data.getTotalElements()/facultyRequest.getLimit());
            FacultyPagingResponse response = new FacultyPagingResponse();
            List<FacultyResponse> listResponse = new ArrayList<>();
            for(Faculty facl : data){
                FacultyResponse faclRes = new FacultyResponse();
                faclRes.setCode(facl.getCode() == null? "":facl.getCode());
                faclRes.setFaculty_id(facl.getId() == null? null:facl.getId());
                faclRes.setFaculty_name(facl.getName() == null? "": facl.getName());
                faclRes.setManager_name(facl.getManager() == null? "": facl.getManager().getFullName());
                faclRes.setManager_id(facl.getManager()== null? null: facl.getManager().getId());
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
            Faculty facl = facultyDao.getOne(facultyRequest.getFaculty_id());
            if(facl.getIs_deleted() == 1){
                return false;
            }
            if(facultyRequest.getManager_id() != null){
                User manager = userDao.getOne(facultyRequest.getManager_id());
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
    
}
