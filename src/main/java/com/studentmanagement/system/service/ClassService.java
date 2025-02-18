package com.studentmanagement.system.service;

import com.studentmanagement.system.entity.Course;
import com.studentmanagement.system.entity.Enrollment;
import com.studentmanagement.system.entity.EnrollmentId;
import com.studentmanagement.system.entity.Student;
import com.studentmanagement.system.entity.ClassEntity;
import com.studentmanagement.system.dto.ClassDTO;
import com.studentmanagement.system.dto.EnrollmentDTO;
import com.studentmanagement.system.dto.StudentDTO;
import com.studentmanagement.system.dto.TeacherDTO;
import com.studentmanagement.system.repository.ClassRepository;
import com.studentmanagement.system.repository.CourseRepository;
import com.studentmanagement.system.repository.EnrollmentRepository;
import com.studentmanagement.system.repository.StudentRepository;
import com.studentmanagement.system.repository.TeacherRepository;
import com.studentmanagement.system.repository.TermRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.studentmanagement.system.entity.Teacher;
import com.studentmanagement.system.entity.Term;
import com.studentmanagement.system.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final TermRepository termRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public ClassService(ClassRepository classRepository, CourseRepository courseRepository,
            TeacherRepository teacherRepository, TermRepository termRepository,
            EnrollmentRepository enrollmentRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.termRepository = termRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
    }

    public List<ClassDTO> getAllClasses() {
        return classRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public ClassDTO createClass(ClassDTO classDTO) {
        Course course = courseRepository.findById(classDTO.getCourseId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id: " + classDTO.getCourseId()));
        Teacher teacher = teacherRepository.findById(classDTO.getTeacherId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Teacher not found with id: " + classDTO.getTeacherId()));
        Term term = termRepository.findById(classDTO.getTermId())
                .orElseThrow(() -> new ResourceNotFoundException("Term not found with id: " + classDTO.getTermId()));

        ClassEntity classEntity = new ClassEntity();
        classEntity.setCourse(course);
        classEntity.setTeacher(teacher);
        classEntity.setTerm(term);
        classEntity.setStartPeriod(classDTO.getStartPeriod());
        classEntity.setEndPeriod(classDTO.getEndPeriod());

        ClassEntity savedClass = classRepository.save(classEntity);
        return convertToDTO(savedClass);
    }

    public ClassDTO updateClass(Long id, ClassDTO classDTO) {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));

        Course course = courseRepository.findById(classDTO.getCourseId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id: " + classDTO.getCourseId()));
        Teacher teacher = teacherRepository.findById(classDTO.getTeacherId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Teacher not found with id: " + classDTO.getTeacherId()));
        Term term = termRepository.findById(classDTO.getTermId())
                .orElseThrow(() -> new ResourceNotFoundException("Term not found with id: " + classDTO.getTermId()));

        classEntity.setCourse(course);
        classEntity.setTeacher(teacher);
        classEntity.setTerm(term);
        classEntity.setStartPeriod(classDTO.getStartPeriod());
        classEntity.setEndPeriod(classDTO.getEndPeriod());

        ClassEntity updatedClass = classRepository.save(classEntity);
        return convertToDTO(updatedClass);
    }

    public ClassDTO getClassById(Long classId) {
        ClassEntity classEntity = classRepository.getClassById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + classId));
        return convertToDTO(classEntity);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public List<StudentDTO> getStudentsForClass(Long id) {
        List<Enrollment> enrollments = enrollmentRepository.findById_ClassId(id);

        List<Student> students = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            students.add(enrollment.getStudent());
        }

        return students.stream().map(this::convertToDTO).toList();
    }

    @Transactional
    public void enrollStudentsInClass(Long classId, List<Long> studentIds) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        List<Student> students = studentRepository.findAllById(studentIds);

        for (Student student : students) {
            Enrollment enrollment = new Enrollment();
            enrollment.setId(new EnrollmentId(student.getId(), classEntity.getId()));
            enrollment.setStudent(student);
            enrollment.setClassEntity(classEntity);
            enrollmentRepository.save(enrollment);
        }
    }

    public List<EnrollmentDTO> getEnrollmentsForClass(Long id) {
        return enrollmentRepository.findById_ClassId(id)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public TeacherDTO getTeacherForClass(Long id) {
        Teacher teacher = classRepository.getTeacherForClass(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found  "));

        return convertToDTO(teacher);
    }

    public List<ClassDTO> getClassesForTeacher(Long teacherId) {
        List <ClassEntity> classes = classRepository.findByTeacher(teacherId);

        return classes.stream().map(this::convertToDTO).toList();
    }

    private ClassDTO convertToDTO(ClassEntity classEntity) {
        ClassDTO dto = new ClassDTO();
        dto.setId(classEntity.getId());
        dto.setCourseId(classEntity.getCourse().getId());
        dto.setCourseName(classEntity.getCourse().getName());
        dto.setTeacherId(classEntity.getTeacher().getId());
        dto.setTeacherName(classEntity.getTeacher().getFirstName() + " " + classEntity.getTeacher().getLastName());
        dto.setTermId(classEntity.getTerm().getId());
        dto.setTermName(classEntity.getTerm().getName() + " " + classEntity.getTerm().getYear());
        dto.setStartPeriod(classEntity.getStartPeriod());
        dto.setEndPeriod(classEntity.getEndPeriod());
        return dto;
    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setStudentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName());
        dto.setClassId(enrollment.getClassEntity().getId());
        dto.setClassName(enrollment.getClassEntity().getCourse().getName());
        dto.setScore(enrollment.getScore());
        return dto;
    }

    public StudentDTO convertToDTO(Student student) {
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

    public TeacherDTO convertToDTO(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setId(teacher.getId());
        teacherDTO.setFirstName(teacher.getFirstName());
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setEmailAddress(teacher.getEmailAddress());
        teacherDTO.setPhoneNumber(teacher.getPhoneNumber());

        return teacherDTO;
    }

}
