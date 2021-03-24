package com.example.server.service;

import java.util.Date;

import com.example.server.dto.MagazineDto;
import com.example.server.entity.Magazine;
import com.example.server.model.request.CreateMagazine;

import org.springframework.stereotype.Component;

@Component
public interface MagazineService {

    Magazine findDatePublished(Date publishedAt);

    Magazine findById(Long id);

    Magazine saveMagazine(MagazineDto magazineDto);

    Magazine saveMagazine(CreateMagazine magazineDto);

    Boolean updateMagazine(CreateMagazine magazineDto);

    Boolean deleteMagazine(Long id);
}
