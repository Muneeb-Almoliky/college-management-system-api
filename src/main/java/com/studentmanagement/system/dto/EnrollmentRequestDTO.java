package com.studentmanagement.system.dto;

import lombok.Data;
import java.util.List;

@Data
public class EnrollmentRequestDTO {
    private List<Long> studentIds; // List of student IDs to enroll
}
