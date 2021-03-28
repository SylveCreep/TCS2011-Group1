package com.example.server.service;

import com.example.server.model.response.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.server.entity.Contribution;
import com.example.server.model.request.*;

public interface ContributionService {
    ContributionPagingResponse getContributionList(ContributionRequest contributionRequest);

    ContributionResponse getContributionById(Long id);

    Boolean deleted(Long id);

    Boolean createContribution(ContributionRequest contribution, MultipartFile file);

    Boolean updateContribution(ContributionRequest contribution, MultipartFile file, int withFile);

    Boolean updateStatusContribution(ContributionRequest contribution);
}