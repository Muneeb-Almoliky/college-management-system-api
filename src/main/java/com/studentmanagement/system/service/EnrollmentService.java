package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.EnrollmentDTO;
import com.studentmanagement.system.entity.Enrollment;
import com.studentmanagement.system.entity.EnrollmentId;
import com.studentmanagement.system.entity.Student;
import com.studentmanagement.system.entity.ClassEntity;
import com.studentmanagement.system.repository.EnrollmentRepository;
import com.studentmanagement.system.repository.StudentRepository;
import com.studentmanagement.system.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, ClassRepository classRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    // Enroll a student in a class
    public EnrollmentDTO enrollStudent(EnrollmentDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        EnrollmentId id = new EnrollmentId(student.getId(), classEntity.getId());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(student);
        enrollment.setClassEntity(classEntity);
        enrollment.setScore(dto.getScore());

        enrollmentRepository.save(enrollment);
        return convertToDTO(enrollment);
    }

    // Get all enrollments
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get enrollments for a specific student
    public List<EnrollmentDTO> getEnrollmentsForStudent(Long studentId) {
        return enrollmentRepository.findById_StudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get enrollments for a specific class
    public List<EnrollmentDTO> getEnrollmentsForClass(Long classId) {
        return enrollmentRepository.findById_ClassId(classId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Update grade
    public EnrollmentDTO updateGrade(Long studentId, Long classId, Double score) {
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(studentId, classId))
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setScore(score);
        enrollmentRepository.save(enrollment);
        return convertToDTO(enrollment);
    }

    //  Convert Entity to DTO
    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setStudentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName());
        dto.setClassId(enrollment.getClassEntity().getId());
        dto.setClassName(enrollment.getClassEntity().getCourse().getName());
        dto.setScore(enrollment.getScore());
        return dto;
    }
}
