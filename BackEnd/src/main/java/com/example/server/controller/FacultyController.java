package com.example.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.server.constant.Constant.*;

import java.util.HashMap;
import java.util.List;

import com.example.server.dao.FacultyDao;
import com.example.server.model.request.*;
import com.example.server.model.response.*;
import com.example.server.service.FacultyService;
import com.example.server.util.ResponseUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/faculties")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyDao facultyDao;

    @Autowired
    private ResponseUtils responseUtils;

    @PreAuthorize("hasRole('R0002') or hasRole('R0003') or hasRole('R0004') or hasRole('R0001')")
    @PostMapping(value = "/filter")
    public ResponseEntity<?> showFacultyBySearch(@RequestBody FacultyRequest facultyRequest) {
        try {
            if (facultyRequest.getLimit() < 0 || facultyRequest.getPage() < 0) {
                return responseUtils.getResponseEntity("NULL", FAILURE,
                        "Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            FacultyPagingResponse facult = facultyService.searchFaculty(facultyRequest);
            if (facult == null) {
                return responseUtils.getResponseEntity(facult, SUCCESS, "Don't have faculty", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(facult.getFacultyResponses(), SUCCESS, "Show faculty success",
                    facult.getLastPage(), HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Show faculty failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002')")
    @DeleteMapping(value = "/{id}", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Must has faculty id", HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = facultyService.deleted(id);
            if (is_deleted == false) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Delete faculty fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Delete faculty successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Delete faculty fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002')")
    @PatchMapping(consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> update(@RequestBody FacultyRequest facultyRequest) {
        try {
            HashMap<String, Object> validateResult = responseUtils.validateFacultyRequest(facultyRequest, 1);
            Object validateRes = validateResult.get("result");
            if (Integer.parseInt(validateRes.toString()) == -1) {
                return responseUtils.getActionResponseEntity("NULL", FAILURE, "Create faculty failed",
                        validateResult, HttpStatus.BAD_REQUEST);
            }
            Boolean facult = facultyService.update(facultyRequest);
            if (facult == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Update faculty fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Update faculty successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Update faculty fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002')")
    @PostMapping
    public ResponseEntity<?> createFaculty(@RequestBody FacultyRequest facultyRequest) {
        try {
            HashMap<String, Object> validateResult = responseUtils.validateFacultyRequest(facultyRequest, 0);
            Object validateRes = validateResult.get("result");
            if (Integer.parseInt(validateRes.toString()) == -1) {
                return responseUtils.getActionResponseEntity("NULL", FAILURE, "Create faculty failed",
                        validateResult, HttpStatus.BAD_REQUEST);
            }
            Boolean isSuccess = facultyService.create(facultyRequest);
            if (isSuccess == false) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Create faculty failed",
                        HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", SUCCESS, "Create faculty success", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Create faculty failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002')")
    @GetMapping(value = "/{id}", consumes = { "text/plain", "application/*" }, produces = "application/json")
    public ResponseEntity<?> getFacultyById(@PathVariable(name = "id") Long id) {
        try {
            if (id == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Must has faculty id", HttpStatus.BAD_REQUEST);
            }
            FacultyResponse facultyResponse = facultyService.getById(id);
            if (facultyResponse == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Get faculty fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(facultyResponse, SUCCESS, "Get faculty successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Get faculty fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('R0002')")
    @GetMapping(value = "/getFacultyHasNoMc", consumes = { "text/plain",
            "application/*" }, produces = "application/json")
    public ResponseEntity<?> getFacultyHasNoMc() {
        try {
            List<FacultyResponse> facultyResponse = facultyService.getFacultyHasNoMc();
            if (facultyResponse == null) {
                return responseUtils.getResponseEntity("NULL", FAILURE, "Get faculty fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(facultyResponse, SUCCESS, "Get faculty successfully", HttpStatus.OK);
        } catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", FAILURE, "Get faculty fail", HttpStatus.BAD_REQUEST);
        }
    }

}
