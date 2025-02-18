package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.ClassDTO;
import com.studentmanagement.system.dto.CourseDTO;
import com.studentmanagement.system.entity.ClassEntity;
import com.studentmanagement.system.entity.Course;
import com.studentmanagement.system.entity.Department;
import com.studentmanagement.system.repository.ClassRepository;
import com.studentmanagement.system.repository.CourseRepository;
import com.studentmanagement.system.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private DepartmentRepository departmentRepository;
    private ClassRepository classRepository;

    public CourseService(CourseRepository courseRepository, DepartmentRepository departmentRepository, ClassRepository classRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.classRepository = classRepository;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public CourseDTO getCourseById(Long id) {
        return this.convertToDTO(courseRepository.getReferenceById(id));
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Department department = departmentRepository.findById(courseDTO.getDepartmentId())
                .orElseThrow(
                        () -> new RuntimeException("Department not found with id: " + courseDTO.getDepartmentId()));

        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        course.setDescription(courseDTO.getDescription());
        course.setDepartment(department);

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        Department department = departmentRepository.findById(courseDTO.getDepartmentId())
                .orElseThrow(
                        () -> new RuntimeException("Department not found with id: " + courseDTO.getDepartmentId()));

        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        course.setDescription(courseDTO.getDescription());
        course.setDepartment(department);

        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<ClassDTO> getClassesForCourse(Long id) {
        return classRepository.findByCourse(id).stream().map(this::convertToDTO).toList();
    }

    public List<CourseDTO> getCoursesByDepartment(Long departmentId) {
        return courseRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setCredits(course.getCredits());
        dto.setDescription(course.getDescription());
        dto.setDepartmentId(course.getDepartment().getId());
        dto.setDepartmentName(course.getDepartment().getName());
        return dto;
    }

    private ClassDTO convertToDTO(ClassEntity classEntity) {
        return new ClassDTO(
                classEntity.getId(),
                classEntity.getCourse().getId(),
                classEntity.getCourse().getName(), classEntity.getTeacher().getId(),
                classEntity.getTeacher().getFirstName() + classEntity.getTeacher().getLastName(),
                classEntity.getTerm().getId(),
                classEntity.getTerm().getName(), classEntity.getStartPeriod(),
                classEntity.getEndPeriod());
    }
}
