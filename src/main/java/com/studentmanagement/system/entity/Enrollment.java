package com.studentmanagement.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@Entity
@Table(name = "student_class")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id; // Composite primary key

    @NotNull(message = "Student is required")
    @ManyToOne
    @MapsId("studentId") // Maps the "studentId" part of the composite key
    @JoinColumn(name = "student_id")
    private Student student;

    @NotNull(message = "Class is required")
    @ManyToOne
    @MapsId("classId") // Maps the "classId" part of the composite key
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 100, message = "Score must not exceed 100")
    @Column(name = "score")
    private Double score;

}
