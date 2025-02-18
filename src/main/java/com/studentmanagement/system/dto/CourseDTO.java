package com.studentmanagement.system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {

    private Long id;
    private String name;
    private Integer credits;
    private String description;
    private Long departmentId;
    private String departmentName;

    
}
