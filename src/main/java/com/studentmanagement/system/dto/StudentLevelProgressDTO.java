package com.studentmanagement.system.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentLevelProgressDTO {
    private Long studentId;
    private String studentName;
    private Long levelId;
    private String levelName; 
    private Long yearId;
    private LocalDate yearStartDate;
    private LocalDate yearEndDate;

    
}

