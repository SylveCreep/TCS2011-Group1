package com.example.server.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.ResponseUtils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.server.constant.Constant;
import com.example.server.dao.MagazineDao;
import com.example.server.dto.MagazineDto;
import com.example.server.entity.Magazine;
import com.example.server.model.request.CreateMagazine;
import com.example.server.model.request.MagazineSearchRequest;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.response.MagazineLastPageResponse;
import com.example.server.model.response.MagazineResponse;
import com.example.server.service.MagazineService;
import com.example.server.util.QueryCheck;
import com.example.server.util.ResponseUtils;

@Service
public class MagazineServiceImpl implements MagazineService {
    @Autowired
    private MagazineDao magazineDao;

    @Autowired
    private QueryCheck queryCheck;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ResponseUtils responseUtils;

    public List<Magazine> findAll(){
        List<Magazine> list = new ArrayList<>();
        magazineDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Magazine findMagazineByFinishedAt(Date finishedAt){
        return magazineDao.findByFinishedAt(finishedAt);
    }

    @Override
    public Magazine findMagazineByPublished_at(Date published_at){
        return magazineDao.findByPublishedAt(published_at);
    }

    @Override
    public Magazine findMagazineByClose_at(Date close_at){
        return magazineDao.findByCloseAt(close_at);
    }

    @Override
    public Magazine findMagazineByTheme(String theme){
        return magazineDao.findByTheme(theme);
    }

    @Override
    public Magazine findById(Long id){
        Magazine magazine = magazineDao.findById(id).get();
        return magazine;
    }

    @Override
    public MagazineResponse findMagazineById(Long id){
        Magazine magazine = magazineDao.findMagazineById(id);
        MagazineResponse magazineResponse = new MagazineResponse();
        magazineResponse.setId(magazine.getId());
        magazineResponse.setCode(magazine.getCode());
        magazineResponse.setTheme(magazine.getTheme());
        magazineResponse.setFinished_at(magazine.getFinished_at());
        magazineResponse.setPublished_at(magazine.getPublished_at());
        magazineResponse.setClose_at(magazine.getClose_at());
        return magazineResponse;
    }

    @Override
    public Magazine saveMagazine(MagazineDto magazineDto){
        try{
            Magazine nMagazine = magazineDto.getMagazineFromDto();
            nMagazine.setCode("M" + String.format("%04d", queryCheck.GetHighestId("magazine")));
            nMagazine.setFinished_at(magazineDto.getFinished_at());
            nMagazine.setPublished_at(magazineDto.getPublished_at());
            nMagazine.setClose_at(magazineDto.getClose_at());
            nMagazine.setTheme(magazineDto.getTheme());
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
            nMagazine.setFinished_at(magazineDto.getFinished_at());
            nMagazine.setPublished_at(magazineDto.getPublished_at());
            nMagazine.setClose_at(magazineDto.getClose_at());
            nMagazine.setTheme(magazineDto.getTheme());
            return magazineDao.save(nMagazine);
        }catch(Exception exception){
            return null;
        }
    }

    @Override
    public Boolean updateMagazine(CreateMagazine magazineDto){
        try {
            Magazine magazine = magazineDao.getOne(magazineDto.getId());
            magazine.setFinished_at(magazineDto.getFinished_at());
            magazine.setTheme(magazineDto.getTheme());
            magazine.setPublished_at(magazineDto.getPublished_at());
            magazine.setClose_at(magazineDto.getClose_at());
            magazineDao.save(magazine);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Boolean updateMagazineCloseAt(CreateMagazine magazineDto){
        try {
            Magazine magazine = magazineDao.getOne(magazineDto.getId());
            magazine.setClose_at(magazineDto.getClose_at());
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

    @Override
    public MagazineLastPageResponse searchMagazineByTheme(MagazineSearchRequest magazineSearchRequest){
        try{
            int offset = magazineSearchRequest.getPage() - 1;
            Sort sort = responseUtils.getSortObj(magazineSearchRequest);
            Page<Magazine> list;
            switch (magazineSearchRequest.getStatus()){
                case 0:
                    list = magazineDao.searchMagazineOpening(magazineSearchRequest.getId(), magazineSearchRequest.getTheme(), magazineSearchRequest.getCode() ,magazineSearchRequest.getCurrentDate(), PageRequest.of(offset, magazineSearchRequest.getLimit(), sort));
                    break;
                case 1:
                    list = magazineDao.searchMagazineProcessing(magazineSearchRequest.getId(), magazineSearchRequest.getTheme(), magazineSearchRequest.getCode() ,magazineSearchRequest.getCurrentDate(), PageRequest.of(offset, magazineSearchRequest.getLimit(), sort));
                    break;
                case 2:
                    list = magazineDao.searchMagazinePublishing(magazineSearchRequest.getId(), magazineSearchRequest.getTheme(), magazineSearchRequest.getCode() ,magazineSearchRequest.getCurrentDate(), PageRequest.of(offset, magazineSearchRequest.getLimit(), sort));
                    break;
                case 3:
                    list = magazineDao.searchMagazineClosing(magazineSearchRequest.getId(), magazineSearchRequest.getTheme(), magazineSearchRequest.getCode() ,magazineSearchRequest.getCurrentDate(), PageRequest.of(offset, magazineSearchRequest.getLimit(), sort));
                    break;
                default:
                    list = null;
                    break;
            }
            
            int lastPage = Math.round(list.getTotalElements() / magazineSearchRequest.getLimit()  + ((list.getTotalElements() % magazineSearchRequest.getLimit() == 0) ? 0 : 1)); 
            MagazineLastPageResponse object = new MagazineLastPageResponse();
            List<MagazineResponse> listResponse = new ArrayList<>();
            for(Magazine magazine:list){
                MagazineResponse magazineResponse = new MagazineResponse();
                magazineResponse.setId(magazine.getId());
                magazineResponse.setTheme(magazine.getTheme() == null ?"":magazine.getTheme());
                magazineResponse.setCode(magazine.getCode() == null ?"": magazine.getCode());
                magazineResponse.setCreated_at(magazine.getCreated_at() == null ?null:magazine.getCreated_at());
                magazineResponse.setFinished_at(magazine.getFinished_at() == null ?null: magazine.getFinished_at());
                magazineResponse.setPublished_at(magazine.getPublished_at() == null ?null: magazine.getPublished_at());
                magazineResponse.setClose_at(magazine.getClose_at() == null ?null: magazine.getClose_at());
                listResponse.add(magazineResponse);
            }
            object.setLastPage(lastPage);
            object.setList(listResponse);
            return object;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

        @Override
        public List<Object> getMagazineListResponse(PagingRequest pagingRequest) {
        try {
            Sort sort = responseUtils.getSortObj(pagingRequest);
            Page<Magazine> list = magazineDao.getNonDelRole(PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), sort));
            int lastPage = Math.round(list.getTotalElements() / pagingRequest.getLimit()  + ((list.getTotalElements() % pagingRequest.getLimit() == 0) ? 0 : 1)); 
            List<Object> object = new ArrayList<>();
            List<MagazineResponse> listResponse = new ArrayList<>();
            for(Magazine magazine: list){
                MagazineResponse magazineResponse = new MagazineResponse();
                magazineResponse.setId(magazine.getId());
                magazineResponse.setTheme(magazine.getTheme() == null ?"":magazine.getTheme());
                magazineResponse.setCode(magazine.getCode() == null ?"":magazine.getCode() );
                magazineResponse.setCreated_at(magazine.getCreated_at() == null ?null:magazine.getCreated_at());
                magazineResponse.setFinished_at(magazine.getFinished_at() == null ?null: magazine.getFinished_at());
                magazineResponse.setPublished_at(magazine.getPublished_at() == null ?null: magazine.getPublished_at());
                magazineResponse.setClose_at(magazine.getClose_at() == null ?null: magazine.getClose_at());
                listResponse.add(magazineResponse);
            }
            object.add(listResponse);
            object.add(lastPage);
            return object;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
