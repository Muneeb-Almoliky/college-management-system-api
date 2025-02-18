package com.studentmanagement.system.controller;

import com.studentmanagement.system.dto.ClassDTO;
import com.studentmanagement.system.dto.EnrollmentDTO;
import com.studentmanagement.system.dto.EnrollmentRequestDTO;
import com.studentmanagement.system.dto.StudentDTO;
import com.studentmanagement.system.service.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        List<ClassDTO> classes = classService.getAllClasses();
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable Long id) {
        return ResponseEntity.ok(classService.getClassById(id));
    }

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@RequestBody ClassDTO classDTO) {
        ClassDTO createdClass = classService.createClass(classDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable Long id, @RequestBody ClassDTO classDTO) {
        ClassDTO updatedClass = classService.updateClass(id, classDTO);
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByClass(@PathVariable Long id) {
        List<StudentDTO> students = classService.getStudentsForClass(id);
        return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
    }

    @GetMapping("/{id}/enrollments")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByClass(@PathVariable Long id) {
        List<EnrollmentDTO> enrollments = classService.getEnrollmentsForClass(id);
        return enrollments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(enrollments);
    }

    @PostMapping("/{id}/enroll")
    public ResponseEntity<String> enrollStudentsInClass(
            @PathVariable Long id,
            @RequestBody EnrollmentRequestDTO request) {
        classService.enrollStudentsInClass(id, request.getStudentIds());
        return ResponseEntity.ok("Students enrolled successfully");
    }

    @GetMapping("/{id}/teacher")
    public ResponseEntity<?> getTeacherByClass(@PathVariable Long id) {
        return ResponseEntity.ok(classService.getTeacherForClass(id));
    }
}
