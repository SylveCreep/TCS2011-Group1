package com.example.server.controller;

import javax.validation.Valid;

import com.example.server.constant.Constant;
import com.example.server.entity.Magazine;
import com.example.server.model.request.CreateMagazine;
import com.example.server.model.request.MagazineSearchRequest;
import com.example.server.model.response.MagazineLastPageResponse;
import com.example.server.model.response.MagazineResponse;
import com.example.server.service.MagazineService;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/magazines")
public class MagazineController {
    @Autowired
    private MagazineService magazineService;

    @Autowired
    private ResponseUtils responseUtils;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody CreateMagazine magazine){
        try{
            Magazine createMagazine = magazineService.saveMagazine(magazine);
            if (createMagazine == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Create magazine failed", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Create magazine successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Create magazine fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", consumes = {"test/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> deleteMagazine(@PathVariable(name="id") Long id){
        try{
            if(id == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has magazine id", HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = magazineService.deleteMagazine(id);
            if(is_deleted == false){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Delete magazine fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Delete magazine successfully", HttpStatus.OK);
        }
        catch(Exception e){
            return responseUtils.getResponseEntity("Null", Constant.FAILURE, "Delete magazine fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(consumes = {"test/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> updateRole(@RequestBody CreateMagazine magazineDto) {
        try{
            if (magazineDto.getId() == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has magazine id", HttpStatus.BAD_REQUEST);
            }
            Boolean magazine = magazineService.updateMagazine(magazineDto);
            if(magazine == false){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update magazine fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(magazine, Constant.SUCCESS, "Update magazine successfully", HttpStatus.OK);
        }
        catch(Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update magazine fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/filter")
    public ResponseEntity<?> showMagazineBySearch(@RequestBody MagazineSearchRequest magazineSearchRequest){
        try{
            if (magazineSearchRequest.getLimit() < 0 || magazineSearchRequest.getPage() < 0){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            MagazineLastPageResponse magazines = magazineService.searchMagazineByTheme(magazineSearchRequest);
            if (magazines == null){
                return responseUtils.getResponseEntity(magazines, Constant.SUCCESS, "Don't have magazine", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(magazines.getList(), Constant.SUCCESS, "Show magazine success", magazines    .getLastPage(), HttpStatus.OK);
        } catch(Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Show magazine failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> getMagazine(@PathVariable(name = "id") Long id){
        try{
            if(id == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has magazine id", HttpStatus.BAD_REQUEST);
            }
            MagazineResponse magazineResponse = magazineService.findMagazineById(id);
            if (magazineResponse == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get magazine fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(magazineResponse, Constant.SUCCESS, "Get magazine successfully", HttpStatus.OK);
        } catch (Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get magazine fail", HttpStatus.BAD_REQUEST);
        }
    }
}
