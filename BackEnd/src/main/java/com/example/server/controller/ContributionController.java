package com.example.server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.server.constant.Constant.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import com.example.server.dao.ContributionDao;
import com.example.server.entity.Contribution;
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

    @Autowired
    private ContributionDao contributionDao;

    @PreAuthorize("hasRole('R0002') or hasRole('R0003') or hasRole('R0004')")
    @PostMapping(value = "/filter")
    public ResponseEntity<?> showContributionBySearch(@RequestBody ContributionRequest contributionRequest) {
        try {
            if (contributionRequest.getLimit() < 0 || contributionRequest.getPage() < 0) {
                return responseUtils.getResponseEntity("NULL", FAILURE,
                        "Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            ContributionPagingResponse contributions = contributionService.getContributionList(contributionRequest);
            if (contributions.getList() == null) {
                return responseUtils.getResponseEntity(contributions, SUCCESS, "Don't have contribution",
                        HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(contributions.getList(), SUCCESS, "Show user success",
                    contributions.getLastPage(), HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Show contribution failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002') or hasRole('R0004')")
    @PostMapping
    public ResponseEntity<?> createContribution(ContributionRequest request, @RequestPart("file") MultipartFile file,
            HttpServletRequest httpServletRequest) {
        try {
            HashMap<String, Object> validateResult = responseUtils.validateContributionRequest(request, file, 0);
            Object validateRes = validateResult.get("result");
            if (Integer.parseInt(validateRes.toString()) == -1) {
                return responseUtils.getActionResponseEntity("NULL", FAILURE, "Create contribution failed",
                        validateResult, HttpStatus.BAD_REQUEST);
            }
            if (file == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Create contribution failed, missing file",
                        HttpStatus.BAD_REQUEST);
            }
            ContributionResponse contributionResponse = contributionService.createContribution(request, file);
            if (contributionResponse == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Create contribution failed",
                        HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(contributionResponse, SUCCESS, "Create contribution success",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Create contribution failed",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0004')")
    @PatchMapping(produces = "application/json")
    public ResponseEntity<?> update(ContributionRequest request, @RequestParam("file") MultipartFile file,
            HttpServletRequest httpServletRequest) {
        try {
            if (request.getId() == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Must has contribution id",
                        HttpStatus.BAD_REQUEST);
            }
            if (request.getWithFile() == null) {
                request.setWithFile(0);
            } else {
                request.setWithFile(1);
            }
            Boolean isUpdated = contributionService.updateContribution(request, file, request.getWithFile());
            if (isUpdated == false) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Update contribution fail",
                        HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Update contribution successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Update contribution fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002') or hasRole('R0003') or hasRole('R0004')")
    @GetMapping(value = "/{id}", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> getContribution(@PathVariable(name = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Must has contribution id",
                        HttpStatus.BAD_REQUEST);
            }
            ContributionResponse contribution = contributionService.getContributionById(id);
            if (contribution == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Get contribution fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(contribution, SUCCESS, "Get contribution successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Get contribution fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0004')")
    @DeleteMapping(value = "/{id}", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Must has contribution id",
                        HttpStatus.BAD_REQUEST);
            }
            Contribution contribution = contributionDao.findExistedContributionById(id);
            if (contribution == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Contribution not existed",
                        HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = contributionService.deleted(contribution);
            if (is_deleted == false) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Delete contribution fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Delete contribution successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Delete contribution fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002') or hasRole('R0003') or hasRole('R0004')")
    @RequestMapping(value = "/download")
    public ResponseEntity<?> downloadContributionByContributionId(
            @RequestParam(name = "contributionId") @Nullable Long contributionId,
            @RequestParam(name = "userId") @Nullable Long userId,
            @RequestParam(name = "magazineId") @Nullable Long magazineId) {
        try {
            int type = 0;
            if (contributionId == null && userId == null && magazineId == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Missing id", HttpStatus.BAD_REQUEST);
            }
            if (contributionId != null && userId == null && magazineId == null) {
                type = 1;
            }
            if (contributionId == null && userId != null && magazineId == null) {
                type = 2;
            }
            if (contributionId == null && userId == null && magazineId != null) {
                type = 3;
            }
            switch (type) {
            case 1:
                ContributionResponse response = contributionService.getContributionById(contributionId);
                if (response == null) {
                    return responseUtils.getResponseEntity("NULL", FAILURE, "Not existed contribution",
                            HttpStatus.BAD_REQUEST);
                }
                HttpHeaders headerContribution = new HttpHeaders();
                headerContribution.add(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=contribution_" + response.getCode() + "." + response.getExtension());

                File fileContribution = fileService.loadContributionPath(response.getCode(), response.getExtension());
                InputStreamResource resource = new InputStreamResource(new FileInputStream(fileContribution));
                return ResponseEntity.ok().headers(headerContribution)
                        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
            case 2:
                List<File> filesUser = fileService.loadContributionPathsByUserIdOrMagazineId(userId, 0);
                HttpHeaders headerUser = new HttpHeaders();
                headerUser.add(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + userId + "_contributions.zip");
                ByteArrayOutputStream baosUser = new ByteArrayOutputStream();
                ZipOutputStream zipOutUser = new ZipOutputStream(baosUser);
                for (File file : filesUser) {
                    FileInputStream fisUser = new FileInputStream(file);
                    ZipEntry zipEntryUser = new ZipEntry(file.getName());
                    zipOutUser.putNextEntry(zipEntryUser);
                    byte[] bytesUser = new byte[1024];
                    int lengthUser;
                    while ((lengthUser = fisUser.read(bytesUser)) >= 0) {
                        zipOutUser.write(bytesUser, 0, lengthUser);
                    }
                    fisUser.close();
                }
                zipOutUser.close();
                baosUser.close();
                return ResponseEntity.ok().headers(headerUser).contentType(MediaType.parseMediaType("application/zip"))
                        .body(baosUser.toByteArray());
            case 3:
                List<File> filesMagazine = fileService.loadContributionPathsByUserIdOrMagazineId(magazineId, 1);
                HttpHeaders header = new HttpHeaders();
                header.add(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + magazineId + "_contributions.zip");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ZipOutputStream zipOut = new ZipOutputStream(baos);
                for (File file : filesMagazine) {
                    FileInputStream fis = new FileInputStream(file);
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                }
                zipOut.close();
                baos.close();
                return ResponseEntity.ok().headers(header).contentType(MediaType.parseMediaType("application/zip"))
                        .body(baos.toByteArray());
            default:
                return responseUtils.getResponseEntity("NULL", FAILURE, "Dowload contribution failed",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtils.getResponseEntity("NULL", FAILURE, "Dowload contribution failed",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002') or hasRole('R0003')")
    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatusByContributionIdAndStatus(@RequestBody ContributionRequest request) {
        try {
            if (request.getId() == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Must has contribution id",
                        HttpStatus.BAD_REQUEST);
            }
            if (contributionService.getContributionById(request.getId()) == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Contribution not existed",
                        HttpStatus.BAD_REQUEST);
            }
            Boolean updateResult = contributionService.updateStatusContribution(request);
            if (updateResult == false) {
                return responseUtils.getResponseEntity("NULL", SUCCESS, "Update fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Update success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Update fail", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getContributionsByMagazineId")
    public ResponseEntity<?> countContributionsByMagazineId(@Nullable @RequestParam("magazineId") Long magazineId) {
        try {
            Long contribution = contributionService.countContributionByMagazineId(magazineId);
            return responseUtils.getResponseEntity(contribution, SUCCESS, "Get contribution success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Get contribution fail", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getContributionsHasNoComment")
    public ResponseEntity<?> getContributionsHasNoComment() {
        try {
            List<ContributionResponse> contributionResponses = contributionService.getContributionListHasNoComment();
            return responseUtils.getResponseEntity(contributionResponses, SUCCESS, "Get contribution success",
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Get contribution fail", HttpStatus.BAD_REQUEST);
        }
    }
}
