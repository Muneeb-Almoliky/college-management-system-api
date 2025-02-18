package com.studentmanagement.system.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TermDTO {

    private Long id;
    private String name; 
    private Long year;
    private LocalDate startDate;
    private LocalDate endDate;
        
}

