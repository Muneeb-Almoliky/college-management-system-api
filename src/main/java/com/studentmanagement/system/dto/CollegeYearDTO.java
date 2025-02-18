package com.studentmanagement.system.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CollegeYearDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;    

}
