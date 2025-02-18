package com.studentmanagement.system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnrollmentDTO {
    private Long studentId;
    private String studentName;
    private Long classId;
    private String className;
    private Double score;

    public EnrollmentDTO(Long studentId, Long classId, Double score) {
        this.studentId = studentId;
        this.classId = classId;
        this.score = score;
    }

    public EnrollmentDTO() {}

}

