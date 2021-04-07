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
import java.util.ResourceBundle;

import com.example.server.dao.*;
import com.example.server.entity.Contribution;
import com.example.server.entity.Magazine;
import com.example.server.entity.User;
import com.example.server.model.request.*;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.SessionUtils.*;
import static com.example.server.util.ResponseUtils.*;

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
            if (contributionRequest.getCreatedAt() != null) {
                hasDate = 1;
            }
            Page<Contribution> list = contributionDao.getContributionList(code, contributionRequest.getStudentId(),studentName,
                    contributionRequest.getFacultyId(), contributionRequest.getMagazineId(),
                    contributionRequest.getCreatedAt(), hasDate, contributionRequest.getStatus(),
                    PageRequest.of(offset, contributionRequest.getLimit(), sort));
            int lastPage = Math.round(list.getTotalElements() / contributionRequest.getLimit()
                    + ((list.getTotalElements() % contributionRequest.getLimit() == 0) ? 0 : 1));
            List<ContributionResponse> contributionResponse = new ArrayList<>();
            for (Contribution contribution : list) {
                ContributionResponse contributionRes = new ContributionResponse();
                contributionRes.setId(contribution.getId());
                contributionRes.setStudentId(contribution.getUser().getId());
                contributionRes.setFacultyId(contribution.getFaculty().getId());
                contributionRes.setFacultyName(contribution.getFaculty().getName());
                contributionRes.setStudentName(contribution.getUser().getFullName());
                contributionRes.setCheckedBy(
                        contribution.getCheckedBy() == null ? null : contribution.getCheckedBy().getId());
                contributionRes.setCheckedByName(
                        contribution.getCheckedBy() == null ? "" : contribution.getCheckedBy().getFullName());
                contributionRes.setCreatedAt(contribution.getCreated_at() == null ? null
                        : convertDateToFormat(contribution.getCreated_at().toString()));
                contributionRes.setStatus(contribution.getIsApproved());
                contributionRes
                        .setMagazineId(contribution.getMagazine() == null ? null : contribution.getMagazine().getId());
                contributionRes.setLinkSource(contribution.getLinkSource());
                contributionRes.setCode(contribution.getCode());
                contributionRes.setEmail(contribution.getUser().getEmail());
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
            String email = getEmail();
            User user = userDao.findExistedUserByEmail(email);
            // User mcUser =
            // userDao.findUserManagerByFacultyIdAndRoleId(user.getFaculty().getId(), (long)
            // 3);
            nContribution.setUser(user);
            // nContribution.setCheckedBy(mcUser);
            nContribution.setFaculty(user.getFaculty());
            if (contribution.getMagazineId() != null) {
                nContribution.setMagazine(magazineDao.findExistedMagazineById(contribution.getMagazineId()));
            }
            nContribution.setCode("C" + String.format("%04d", queryCheck.GetHighestId("contribution")));
            nContribution.setCreated_at(new Date());
            FileResponse fileResponse = fileService.storeContribution(file, nContribution.getCode());
            nContribution.setLinkSource("contribution_"+nContribution.getCode()+"."+fileResponse.getExtension());
            nContribution.setExtension(fileResponse.getExtension());

            // User mmUser = userDao.findUserManagerByFacultyIdAndRoleId(null, (long) 2);
            // List<String> ccList = new ArrayList<>();
            // ccList.add(mmUser.getEmail());
            // String subject = "New commited contribution from student "+
            // user.getFullName() + " (code:" + user.getCode() + ")";
            // String html = "<p>Student " + user.getFullName()+ " has commited following
            // code "+ nContribution.getCode()+ " contribution</p>"+"<p>Please follow url
            // link to see contribution: "+ "http://..." +"</p>";
            try {
                contributionDao.save(nContribution);
                // mailService.sendAsHtml(host_email, mcUser.getEmail(), ccList, subject, html);
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
            if (uContribution == null) {
                return null;
            }
            uContribution.setUpdated_at(new Date());
            if (contribution.getCheckedBy() != null) {
                uContribution.setCheckedBy(userDao.findByUserId(contribution.getCheckedBy()));
            }
            if (contribution.getMagazineId() != null) {
                uContribution.setMagazine(magazineDao.findExistedMagazineById(contribution.getMagazineId()));
            }
            if (contribution.getStatus() != null) {
                uContribution.setIsApproved(contribution.getStatus());
            }
            FileResponse fileResponse = fileService.storeContribution(file, uContribution.getCode());
            uContribution.setLinkSource(fileResponse.getPath().substring(fileResponse.getPath().lastIndexOf("/", 0), fileResponse.getPath().length()));
            uContribution.setExtension(fileResponse.getExtension());
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
            User checkByUser = userDao.findExistedUserByEmail(getEmail());
            uContribution.setIsApproved(contribution.getStatus());
            uContribution.setCheckedBy(checkByUser);
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
            if (contribution == null) {
                return null;
            }
            ContributionResponse contributionRes = new ContributionResponse();
            contributionRes.setId(contribution.getId());
            contributionRes.setStudentId(contribution.getUser().getId());
            contributionRes.setFacultyId(contribution.getFaculty().getId());
            contributionRes.setFacultyName(contribution.getFaculty().getName());
            contributionRes.setStudentName(contribution.getUser().getFullName());
            contributionRes
                    .setCheckedBy(contribution.getCheckedBy() == null ? null : contribution.getCheckedBy().getId());
            contributionRes.setCheckedByName(
                    contribution.getCheckedBy() == null ? "" : contribution.getCheckedBy().getFullName());
            contributionRes.setCreatedAt(contribution.getCreated_at() == null ? null
                    : convertDateToFormat(contribution.getCreated_at().toString()));
            contributionRes.setStatus(contribution.getIsApproved());
            contributionRes
                    .setMagazineId(contribution.getMagazine() == null ? null : contribution.getMagazine().getId());
            contributionRes.setLinkSource(contribution.getLinkSource());
            contributionRes.setCode(contribution.getCode());
            contributionRes.setExtension(contribution.getExtension());
            contributionRes.setEmail(contribution.getUser().getEmail());
            return contributionRes;
        } catch (Exception e) {
            return null;
        }
    }

}