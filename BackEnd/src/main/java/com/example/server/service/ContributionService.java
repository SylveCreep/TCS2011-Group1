package com.example.server.service;

import com.example.server.model.response.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.example.server.entity.Contribution;
import com.example.server.model.request.*;

public interface ContributionService {
    ContributionPagingResponse getContributionList(ContributionRequest contributionRequest);

    ContributionResponse getContributionById(Long id);

    Boolean deleted(Contribution contribution);

    ContributionResponse createContribution(ContributionRequest contribution, MultipartFile file);

    Boolean updateContribution(ContributionRequest contribution, MultipartFile file, int withFile);

    Boolean updateStatusContribution(ContributionRequest contribution);

    Long countContributionByMagazineId(Long magazineId);

    ContributionPagingResponse getContributionListHasNoComment(ContributionRequest contribution);
}
