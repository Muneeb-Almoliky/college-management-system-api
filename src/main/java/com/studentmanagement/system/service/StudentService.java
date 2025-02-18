
package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.ClassDTO;
import com.studentmanagement.system.dto.EnrollmentDTO;
import com.studentmanagement.system.dto.StudentDTO;
import com.studentmanagement.system.entity.ClassEntity;
import com.studentmanagement.system.entity.Enrollment;
import com.studentmanagement.system.entity.EnrollmentId;
import com.studentmanagement.system.entity.Student;
import com.studentmanagement.system.exception.ResourceNotFoundException;
import com.studentmanagement.system.repository.ClassRepository;
import com.studentmanagement.system.repository.EnrollmentRepository;
import com.studentmanagement.system.repository.StudentRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private EnrollmentRepository enrollmentRepository;
    private ClassRepository classRepository;

    public StudentService(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository,
            ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.classRepository = classRepository;
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setMiddleName(studentDTO.getMiddleName());
        student.setLastName(studentDTO.getLastName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setGender(studentDTO.getGender());
        student.setEnrolmentDate(studentDTO.getEnrolmentDate());
        student = studentRepository.save(student);

        return convertToDTO(student);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found "));

        return convertToDTO(student);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        student.setFirstName(studentDTO.getFirstName());
        student.setMiddleName(studentDTO.getMiddleName());
        student.setLastName(studentDTO.getLastName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setGender(studentDTO.getGender());
        student.setEnrolmentDate(studentDTO.getEnrolmentDate());
        student = studentRepository.save(student);

        return this.convertToDTO(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<ClassDTO> getClassesForStudent(Long studentId, Long levelId, Long termId) {
        List<ClassEntity> classes;

        if (levelId != null && termId != null) {
            classes = enrollmentRepository.findClassesByStudentIdAndLevelAndTerm(studentId, levelId, termId);
        } else if (levelId != null) {
            classes = enrollmentRepository.findClassesByStudentIdAndLevel(studentId, levelId);
        } else if (termId != null) {
            classes = enrollmentRepository.findClassesByStudentIdAndTerm(studentId, termId);
        } else {
            classes = enrollmentRepository.findClassesByStudentId(studentId);
        }

        return classes.stream().map(this::converToDto).collect(Collectors.toList());
    }

 
    @Transactional
    public EnrollmentDTO enrollStudentInClass(Long studentId, Long classId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // Create new student-class association
        EnrollmentId enrollmentId = new EnrollmentId(studentId, classId);
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);
        enrollment.setStudent(student);
        enrollment.setClassEntity(classEntity);
        enrollment.setScore(null); // Default no score

        enrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(enrollment);
    }

    public List<ClassDTO> getClassesForStudent(Long studentId) {

        List<Enrollment> enrollments = enrollmentRepository.findById_StudentId(studentId);

        List<ClassEntity> classes = new ArrayList();

        for (Enrollment enrollment : enrollments) {
            ClassEntity classEntity = enrollment.getClassEntity();
            classes.add(classEntity);
        }

        return classes.stream().map(this::converToDto).collect(Collectors.toList());
    }

    public List<StudentDTO> getFilteredStudents(Long departmentId, Long levelId) {
        List<Student> students;

        if (departmentId != null && levelId != null) {
            students = studentRepository.findByDepartmentAndLevel(departmentId, levelId);
        } else if (departmentId != null) {
            students = studentRepository.findByDepartmentId(departmentId);
        } else {
            students = studentRepository.findAll(); // No filter applied
        }

        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<EnrollmentDTO> getEnrollmentsForStudent(Long studentId) {
        return enrollmentRepository.findById_StudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> getStudentsByDepartmentId(Long departmentId) {
        return studentRepository.findStudentsByDepartmentId(departmentId).stream().map(this::convertToDTO).toList();
    }

    // public List<CollegeYearDTO> getStudentCollegeYears(Long studentId) {
    // return collegeYearService.getStudentCollegeYears(studentId);
    // }

    // public List<CollegeLevelDTO> getStudentCollegeLevels(Long studentId) {
    // return collegeLevelService.getStudentCollegeLevels(studentId);
    // }

    private StudentDTO convertToDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setMiddleName(student.getMiddleName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        studentDTO.setGender(student.getGender());
        studentDTO.setEnrolmentDate(student.getEnrolmentDate());

        return studentDTO;
    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setClassId(enrollment.getClassEntity().getId());
        dto.setClassName(enrollment.getClassEntity().getCourse().getName());
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setStudentName(enrollment.getStudent().getFirstName() + enrollment.getStudent().getLastName());
        dto.setScore(enrollment.getScore());
        return dto;
    }

    private ClassDTO converToDto(ClassEntity classEntity) {
        if (classEntity == null) {
            return null;
        }
        return new ClassDTO(
                classEntity.getId(),
                classEntity.getCourse().getId(),
                classEntity.getCourse().getName(),
                classEntity.getTeacher().getId(),
                classEntity.getTeacher().getFirstName(),
                classEntity.getTerm().getId(),
                classEntity.getTerm().getName(),
                classEntity.getStartPeriod(),
                classEntity.getEndPeriod());
    }

}
