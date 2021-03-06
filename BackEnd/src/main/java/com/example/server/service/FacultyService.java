package com.example.server.service;

import com.example.server.model.request.*;
import com.example.server.model.response.*;

public interface FacultyService {
    FacultyPagingResponse searchFaculty(FacultyRequest facultyRequest);

    Boolean update(FacultyRequest facultyRequest);

    Boolean deleted(Long id);
}
