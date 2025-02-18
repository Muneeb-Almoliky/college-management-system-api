package com.studentmanagement.system.controller;

import com.studentmanagement.system.dto.EnrollmentDTO;
import com.studentmanagement.system.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@PreAuthorize("hasRole('ADMIN')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enrollStudent(@RequestBody EnrollmentDTO dto) {
        return ResponseEntity.ok(enrollmentService.enrollStudent(dto));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsForStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsForStudent(studentId));
    }

    @GetMapping("/classes/{classId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsForClass(@PathVariable Long classId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsForClass(classId));
    }

    @PutMapping("/{studentId}/{classId}")
    public ResponseEntity<EnrollmentDTO> updateGrade(
            @PathVariable Long studentId,
            @PathVariable Long classId,
            @RequestParam Double score) {
        return ResponseEntity.ok(enrollmentService.updateGrade(studentId, classId, score));
    }
}
