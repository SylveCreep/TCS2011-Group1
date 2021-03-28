package com.example.server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.server.constant.Constant.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import com.example.server.model.request.ContributionRequest;
import com.example.server.model.response.ContributionPagingResponse;
import com.example.server.model.response.ContributionResponse;
import com.example.server.service.ContributionService;
import com.example.server.service.FileService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/contributions")
public class ContributionController {
    
    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private FileService fileService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value="/filter")
    public ResponseEntity<?> showContributionBySearch(@RequestBody ContributionRequest contributionRequest){
        try {
            if(contributionRequest.getLimit() < 0 || contributionRequest.getPage() < 0){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            ContributionPagingResponse contributions = contributionService.getContributionList(contributionRequest);
            if(contributions.getList() == null){
                return responseUtils.getResponseEntity(contributions, SUCCESS,"Don't have user", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(contributions.getList(), SUCCESS,"Show user success", contributions.getLastPage(), HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE,"Show user failed", HttpStatus.BAD_REQUEST);
        }
    }

    // @RequestMapping(value = "/download/{id}")
    // public ResponseEntity<?> downloadContribution(@PathVariable(name="id") Long id) {
    //     try {
    //         if(id == null){
    //             return responseUtils.getResponseEntity("NULL", FAILURE,"Missing id", HttpStatus.BAD_REQUEST);
    //         }
    //         ContributionResponse response = contributionService.getContributionById(id);
    //         if(response == null){
    //             return responseUtils.getResponseEntity("NULL", FAILURE,"Not existed contribution", HttpStatus.BAD_REQUEST);
    //         }
    //         HttpHeaders header = new HttpHeaders();
    //         header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contribution_"+response.getCode()+"."+response.getExtension());
    
    //         File file = fileService.loadContributionPath(response.getCode(), response.getExtension());
    //         InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    //         return ResponseEntity.ok()
    //                 .headers(header)
    //                 .contentType(MediaType.parseMediaType("application/octet-stream"))
    //                 .body(resource);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return responseUtils.getResponseEntity("NULL", FAILURE,"Dowload user failed", HttpStatus.BAD_REQUEST);
    //     }
    // }

    @PostMapping
    public ResponseEntity<?> createContribution(ContributionRequest request,@RequestPart("file") MultipartFile file, HttpServletRequest httpServletRequest){
        try {
            // HashMap<String, Object> validateResult = responseUtils.validateCreateAccountRequest(user, file, 0);
            // Object validateRes = validateResult.get("result");
            // if(Integer.parseInt(validateRes.toString()) == -1){
            //     return responseUtils.getActionResponseEntity("NULL", Constant.FAILURE,"Create user failed",validateResult, HttpStatus.BAD_REQUEST);
            // }
            if(file == null){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Create contribution failed, missing file", HttpStatus.BAD_REQUEST);
            }
            Boolean isCreated = contributionService.createContribution(request, file);
            if(isCreated == false){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Create contribution failed", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS,"Create contribution success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE,"Create contribution failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(produces = "application/json")
    public ResponseEntity<?> update(ContributionRequest request, @RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest){
        try {
            // HashMap<String, Object> validateResult = responseUtils.validateCreateAccountRequest(userDto, file, 1);
            // Object validateRes = validateResult.get("result");
            // if(Integer.parseInt(validateRes.toString()) == -1){
            //     return responseUtils.getActionResponseEntity("NULL", Constant.FAILURE,"Update user failed",validateResult, HttpStatus.BAD_REQUEST);
            // }
            if(request.getId() == null){
                return responseUtils.getResponseEntity("NULL", FAILURE ,"Must has contribution id", HttpStatus.BAD_REQUEST);
            }
            if(request.getWithFile() == null){
                request.setWithFile(0);
            } else {
                request.setWithFile(1);
            }
            Boolean isUpdated = contributionService.updateContribution(request, file, request.getWithFile());
            if(isUpdated == false){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Update contribution fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS,"Update contribution successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE,"Update contribution fail", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/{id}",consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> getContribution(@PathVariable(name="id") Long id){
        try {
            if(id == null){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Must has contribution id", HttpStatus.BAD_REQUEST);
            }
            ContributionResponse contribution = contributionService.getContributionById(id);
            if(contribution == null){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Get contribution fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(contribution, SUCCESS,"Get contribution successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE,"Get contribution fail", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value="/{id}",consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name="id") Long id){
        try {
            if(id == null){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Must has contribution id", HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = contributionService.deleted(id);
            if(is_deleted == false){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Delete contribution fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS,"Delete contribution successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE,"Delete contribution fail", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/download/{id}")
    public ResponseEntity<?> downloadContribution(@PathVariable(name="id") Long id) {
        try {
            if(id == null){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Missing id", HttpStatus.BAD_REQUEST);
            }
            ContributionResponse response = contributionService.getContributionById(id);
            if(response == null){
                return responseUtils.getResponseEntity("NULL", FAILURE,"Not existed contribution", HttpStatus.BAD_REQUEST);
            }
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contribution_"+response.getCode()+"."+"zip");
    
            File file = fileService.loadContributionPath(response.getCode(), response.getExtension());
            ByteArrayOutputStream  baos = new ByteArrayOutputStream ();
            ZipOutputStream zipOut = new ZipOutputStream(baos);
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.close();
            fis.close();
            baos.close();
            return ResponseEntity.ok()
                    .headers(header)
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .body(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtils.getResponseEntity("NULL", FAILURE,"Dowload user failed", HttpStatus.BAD_REQUEST);
        }
    }
}
