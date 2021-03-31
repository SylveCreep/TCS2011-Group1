package com.example.server.service;

import java.util.List;

import com.example.server.model.request.*;
import com.example.server.model.response.*;

public interface FacultyService {
    FacultyPagingResponse searchFaculty(FacultyRequest facultyRequest);

    Boolean update(FacultyRequest facultyRequest);

    Boolean deleted(Long id);

    Boolean create(FacultyRequest facultyRequest);

    FacultyResponse getById(Long id);

    List<FacultyResponse> getFacultyHasNoMc();


}
