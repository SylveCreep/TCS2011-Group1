package com.example.server.service;

import java.util.Date;
import java.util.List;

import com.example.server.dto.MagazineDto;
import com.example.server.entity.Magazine;
import com.example.server.model.request.CreateMagazine;
import com.example.server.model.request.MagazineSearchRequest;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.response.ContributionByMagazineResponse;
import com.example.server.model.response.MagazineLastPageResponse;
import com.example.server.model.response.MagazineResponse;

import org.springframework.stereotype.Component;

@Component
public interface MagazineService {
    Magazine findMagazineByFinishedAt(Date finishedAt);

    Magazine findMagazineByPublished_at(Date published_at);

    Magazine findMagazineByClose_at(Date close_at);

    Magazine findMagazineByTheme(String theme);

    Magazine findById(Long id);

    MagazineResponse findMagazineById(Long id);

    Magazine saveMagazine(MagazineDto magazineDto);

    Magazine saveMagazine(CreateMagazine magazineDto);

    Boolean updateMagazine(CreateMagazine magazineDto);

    Boolean updateMagazineCloseAt(CreateMagazine magazineDto);

    Boolean deleteMagazine(Long id);

    MagazineLastPageResponse searchMagazineByTheme(MagazineSearchRequest magazineSearchRequest);

    List<Object> getMagazineListResponse(PagingRequest pagingRequest);

    List<ContributionByMagazineResponse> getCountContributionByMagazine();
}
