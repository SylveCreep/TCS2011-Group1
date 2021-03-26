package com.example.server.service;

import com.example.server.model.response.*;
import com.example.server.model.request.*;

public interface ContributionService {
    ContributionPagingResponse getContributionList(ContributionRequest contributionRequest);

    Boolean deleted(Long id);
}
