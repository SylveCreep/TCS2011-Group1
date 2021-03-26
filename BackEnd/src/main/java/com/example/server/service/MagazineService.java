package com.example.server.service;

import java.util.Date;

import com.example.server.dto.MagazineDto;
import com.example.server.entity.Magazine;
import com.example.server.model.request.CreateMagazine;

import org.springframework.stereotype.Component;

@Component
public interface MagazineService {
    Magazine findMagazineByOpen_at(Date open_at);

    Magazine findMagazineByPublished_at(Date published_at);

    Magazine findMagazineByClose_at(Date close_at);

    Magazine findMagazineByTheme(String theme);

    Magazine findById(Long id);

    Magazine saveMagazine(MagazineDto magazineDto);

    Magazine saveMagazine(CreateMagazine magazineDto);

    Boolean updateMagazine(CreateMagazine magazineDto);

    Boolean deleteMagazine(Long id);
}
