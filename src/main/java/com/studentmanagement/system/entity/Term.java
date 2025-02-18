package com.studentmanagement.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
 

@Setter
@Getter
@Entity
@Table(name = "term")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Term name is required")
    @Size(min = 3, max = 50, message = "Term name must be between 3-50 characters")    
    private String name; 

    @NotNull(message = "Year is required")
    @ManyToOne
    @JoinColumn(name = "year_id")
    private CollegeYear year;

    @NotNull(message = "Starte date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
}

