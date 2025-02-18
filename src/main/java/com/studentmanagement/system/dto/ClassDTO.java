package com.studentmanagement.system.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassDTO {

    private Long id;
    private Long courseId;
    private String courseName;
    private Long teacherId;
    private String teacherName;
    private Long termId;
    private String termName;
    private LocalTime startPeriod;
    private LocalTime endPeriod;

    public ClassDTO() {
    }

    public ClassDTO(Long id, Long courseId, String courseName, Long teacherId, String teacherName, Long termId, String termName, LocalTime startPeriod, LocalTime endPeriod) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.termId = termId;
        this.termName = termName;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

}


