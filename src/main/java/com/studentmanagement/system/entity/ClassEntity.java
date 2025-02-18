package com.studentmanagement.system.entity;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "class")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Course is required")
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @NotNull(message = "Teacher is required")
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @NotNull(message = "Term is required")
    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    
    @ManyToOne
    @JoinColumn(name = "level_id")
    private CollegeLevel level;

    @NotNull(message = "Start period is required")
    @Column(nullable = false)
    private LocalTime startPeriod;

    @NotNull(message = "End period is required")
    @Column(nullable = false)
    private LocalTime endPeriod;

    @AssertTrue(message = "Start period must be before end period")
    public boolean isStartPeriodBeforeEndPeriod() {
        return startPeriod.isBefore(endPeriod);
    }
}