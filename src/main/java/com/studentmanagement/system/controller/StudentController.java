package com.studentmanagement.system.controller;

import com.studentmanagement.system.dto.*;
import com.studentmanagement.system.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //  Get students (filtered by department or level if params are provided)
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getFilteredStudents(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long levelId) {
        List<StudentDTO> students = studentService.getFilteredStudents(departmentId, levelId);
        return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
    }

    //  Create a student
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    //  Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    //  Update student
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    //  Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    //  Get classes for a student (with optional filters: level and term)
    @GetMapping("/{id}/classes")
    public ResponseEntity<List<ClassDTO>> getStudentClasses(
            @PathVariable Long id,
            @RequestParam(required = false) Long levelId,
            @RequestParam(required = false) Long termId) {
        List<ClassDTO> classes = studentService.getClassesForStudent(id, levelId, termId);
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    //  Get enrollments
    @GetMapping("/{id}/enrollments")
    public ResponseEntity<List<EnrollmentDTO>> getStudentEnrollments(@PathVariable Long id) {
        List<EnrollmentDTO> enrollments = studentService.getEnrollmentsForStudent(id);
        return enrollments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(enrollments);
    }

    //  Enroll a student in a class
    @PostMapping("/{studentId}/enroll/{classId}")
    public ResponseEntity<EnrollmentDTO> enrollStudentInClass(@PathVariable Long studentId,
                                                              @PathVariable Long classId) {
        EnrollmentDTO enrollment = studentService.enrollStudentInClass(studentId, classId);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
    }
}
