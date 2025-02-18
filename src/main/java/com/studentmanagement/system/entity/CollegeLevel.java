package com.studentmanagement.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "college_level")
public class CollegeLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Level name is required")
    @Size(min = 3, max = 50, message = "Level name must be between 3-50 characters")
    private String levelName;

    @NotBlank(message = "Level order is required")
    private Integer levelOrder; // Determines progression order

    @ManyToOne
    @JoinColumn(name = "next_level_id")
    private CollegeLevel nextLevel; // Self-referential for progression

    @OneToMany(mappedBy = "level")
    private List<ClassEntity> classes;

    @ManyToMany(mappedBy = "levels")
    private List<Course> courses; //  Courses offered in this level

    @OneToMany(mappedBy = "collegeLevel")
    private List<StudentLevelProgress> studentYearLevels;

    
}

