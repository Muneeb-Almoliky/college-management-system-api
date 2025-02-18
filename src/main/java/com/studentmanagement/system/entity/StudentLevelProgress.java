package com.studentmanagement.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_year_level")
public class StudentLevelProgress {

    @EmbeddedId
    private StudentLevelProgressId id;

    @NotNull(message = "Student is required")
    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull(message = "Level is required")
    @ManyToOne
    @MapsId("levelId")
    @JoinColumn(name = "level_id", nullable = false)
    private CollegeLevel collegeLevel;

    @NotNull(message = "Year is required")
    @ManyToOne
    @MapsId("yearId")
    @JoinColumn(name = "year_id", nullable = false)
    private CollegeYear collegeYear;
    
}
