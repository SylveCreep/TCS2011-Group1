package com.example.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.ResponseUtils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.server.constant.Constant;
import com.example.server.dao.MagazineDao;
import com.example.server.dto.MagazineDto;
import com.example.server.entity.Magazine;
import com.example.server.model.request.CreateMagazine;
import com.example.server.service.MagazineService;
import com.example.server.util.QueryCheck;

@Service
public class MagazineServiceImpl implements MagazineService {
    @Autowired
    private MagazineDao magazineDao;

    @Autowired
    private QueryCheck queryCheck;

    public List<Magazine> findAll(){
        List<Magazine> list = new ArrayList<>();
        magazineDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Magazine findByDateSubmit(Date submitAt){
        return magazineDao.findMagazineBySubmitAt(submitAt);
    }

    @Override
    public Magazine findDatePublished(Date publishedAt){
        return magazineDao.findMagazineByPublishedAt(publishedAt);
    }

    @Override
    public Magazine findById(Long id){
        Magazine magazine = magazineDao.findMagazineById(id);
        return magazine;
    }

    @Override
    public Magazine saveMagazine(MagazineDto magazineDto){
        try{
            Magazine nMagazine = magazineDto.getMagazineFromDto();
            nMagazine.setCode("M" + String.format("%04d", queryCheck.GetHighestId("magazine")));
            nMagazine.setPublishedAt(magazineDto.getPublishedAt());
            nMagazine.setSubmitAt(magazineDto.getSubmitAt());
            nMagazine.setContribution(magazineDto.getContribution());
            return magazineDao.save(nMagazine);
        }catch(Exception exception){
            return null;
        }
    }

    @Override
    public Magazine saveMagazine(CreateMagazine magazineDto){
        try{
            Magazine nMagazine = new Magazine();
            nMagazine.setCode("M" + String.format("%04d", queryCheck.GetHighestId("magazine")));
            nMagazine.setPublishedAt(magazineDto.getPublishedAt());
            nMagazine.setSubmitAt(magazineDto.getSubmitAt());
            nMagazine.setContribution(magazineDto.getContribution());
            return magazineDao.save(nMagazine);
        }catch(Exception exception){
            return null;
        }
    }

    @Override
    public Boolean updateMagazine(CreateMagazine magazineDto){
        try {
            Magazine magazine = magazineDao.getOne(magazineDto.getId());
            magazine.setCode(magazineDto.getCode());
            magazine.setPublishedAt(magazineDto.getPublishedAt());
            magazine.setSubmitAt(magazineDto.getSubmitAt());
            magazine.setContribution(magazineDto.getContribution());
            magazineDao.save(magazine);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Boolean deleteMagazine(Long id){
        try{
            Magazine magazine = magazineDao.getOne(id);
            magazine.setIs_deleted(Constant.DELETED);
            magazineDao.save(magazine);
            return true;
        } catch(Exception exception){
            return false;
        }
    }
}
