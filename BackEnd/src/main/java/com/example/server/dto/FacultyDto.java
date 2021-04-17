package com.example.server.dto;

import com.example.server.entity.Faculty;

public class FacultyDto {
    
    private String code;
    private String name;

    public Faculty getFacultyFromDto(){
        Faculty faculty = new Faculty();
        faculty.setCode(code);
        faculty.setName(name);
        return faculty;
    }
}
