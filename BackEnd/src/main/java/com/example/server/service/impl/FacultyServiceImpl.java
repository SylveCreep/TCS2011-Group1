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
import java.util.stream.Stream;

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
            if (facultyRequest.getStartDate() != null && facultyRequest.getEndDate() != null) {
                hasDate = 1;
                startDate = facultyRequest.getStartDate();
                endDate = facultyRequest.getEndDate();
            }
            Page<Faculty> data = facultyDao.searchFaculty(facultyRequest.getCode(), facultyRequest.getFacultyName(),
                    startDate, endDate, hasDate, PageRequest.of(offset, facultyRequest.getLimit(), sort));
            int lastPage = Math.round(data.getTotalElements() / facultyRequest.getLimit()
                    + ((data.getTotalElements() % facultyRequest.getLimit() == 0) ? 0 : 1));
            List<User> mcList = userDao.findMCByRoleId((long) 3);
            FacultyPagingResponse response = new FacultyPagingResponse();
            List<FacultyResponse> listResponse = new ArrayList<>();
            for (Faculty facl : data) {
                Optional<User> mc = mcList.stream().filter(user -> user.getFaculty().getId() == facl.getId()).findAny();
                FacultyResponse faclRes = new FacultyResponse();
                faclRes.setCode(facl.getCode() == null ? "" : facl.getCode());
                faclRes.setFacultyId(facl.getId() == null ? null : facl.getId());
                faclRes.setFacultyName(facl.getName() == null ? "" : facl.getName());
                faclRes.setMcId(mc.isEmpty() ? null : mc.get().getId());
                faclRes.setMcName(mc.isEmpty() ? "" : mc.get().getFullName());
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
            Faculty facl = facultyDao.findExistedFacultyById(facultyRequest.getFacultyId());
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
            if (falc != null) {
                if (falc.getIs_deleted() == NOTDELETED) {
                    List<User> mcList = userDao.findMCByRoleId((long) 3);
                    Optional<User> mc = mcList.stream().filter(user -> user.getFaculty().getId() == falc.getId())
                            .findAny();
                    FacultyResponse faculResponse = new FacultyResponse();
                    faculResponse.setCode(falc.getCode() == null ? "" : falc.getCode());
                    faculResponse.setFacultyId(falc.getId());
                    faculResponse.setFacultyName(falc.getName() == null ? "" : falc.getName());
                    faculResponse.setMcId(mc.isEmpty() ? null : mc.get().getId());
                    faculResponse.setMcName(mc.isEmpty() ? "" : mc.get().getFullName());
                    return faculResponse;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<FacultyResponse> getFacultyHasNoMc() {
        try {
            List<Faculty> faculList = facultyDao.getFacultyHasNoMC();
            List<FacultyResponse> facultyResponses = new ArrayList<>();
            for (Faculty facul : faculList) {
                FacultyResponse faculResponse = new FacultyResponse();
                faculResponse.setCode(facul.getCode() == null ? "" : facul.getCode());
                faculResponse.setFacultyId(facul.getId());
                faculResponse.setFacultyName(facul.getName() == null ? "" : facul.getName());
                facultyResponses.add(faculResponse);
            }
            return facultyResponses;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<StudentsByFacultyResponse> getCountStudentsByFaculty() {
        try {
            List<Object> list = facultyDao.countStudentsByFaculty();
            List<StudentsByFacultyResponse> studentsByFacultyResponses = new ArrayList<>();
            for (Object object : list) {
                StudentsByFacultyResponse studentsByFacultyResponse = new StudentsByFacultyResponse();
                Object[] obj = (Object[]) object;
                if (((Object[]) obj)[0] != null) {
                    studentsByFacultyResponse.setTotalStudents(Integer.parseInt(((Object[]) obj)[0].toString()));
                }
                if (((Object[]) obj)[1] != null) {
                    studentsByFacultyResponse.setFacultyName(((Object[]) obj)[1].toString());
                }
                studentsByFacultyResponses.add(studentsByFacultyResponse);
            }
            return studentsByFacultyResponses;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ContributionByFaculty> getCountContributionsByFaculty() {
        try {
            List<Object> list = facultyDao.countContributionsByFaculty();
            List<ContributionByFaculty> contributionsByFacultyResponses = new ArrayList<>();
            for (Object object : list) {
                ContributionByFaculty contributionsByFacultyResponse = new ContributionByFaculty();
                Object[] obj = (Object[]) object;
                if (((Object[]) obj)[0] != null) {
                    contributionsByFacultyResponse
                            .setTotalContributions(Integer.parseInt(((Object[]) obj)[0].toString()));
                }
                if (((Object[]) obj)[1] != null) {
                    contributionsByFacultyResponse.setFacultyName(((Object[]) obj)[1].toString());
                }
                contributionsByFacultyResponses.add(contributionsByFacultyResponse);
            }
            return contributionsByFacultyResponses;
        } catch (Exception e) {
            return null;
        }
    }

}
