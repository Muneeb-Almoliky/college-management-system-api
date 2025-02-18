package com.studentmanagement.system.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class EnrollmentId implements Serializable {
    private Long studentId;
    private Long classId;

    public EnrollmentId() {}

    public EnrollmentId(Long studentId, Long classId) {
        this.studentId = studentId;
        this.classId = classId;
    }

}
