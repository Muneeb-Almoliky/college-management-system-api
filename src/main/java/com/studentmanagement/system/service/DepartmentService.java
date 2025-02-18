package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.CourseDTO;
import com.studentmanagement.system.dto.DepartmentDTO;
import com.studentmanagement.system.dto.StudentDTO;
import com.studentmanagement.system.entity.Department;
import com.studentmanagement.system.repository.DepartmentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;
    private StudentService studentService;
    private CourseService courseService;
    
    public DepartmentService(DepartmentRepository departmentRepository, StudentService studentService, CourseService courseService) {
        this.departmentRepository = departmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::convertToDTO).toList();
    }

    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.getReferenceById(id);
        return convertToDTO(department);
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        Department updatedDepartment = departmentRepository.save(department);
        return convertToDTO(updatedDepartment);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public List<StudentDTO> getStudentsByDepartmentId(Long departmentId) {
        return studentService.getStudentsByDepartmentId(departmentId);
    }

    public List<CourseDTO> getCoursesByDepartmentId(Long departmentId) {
        return courseService.getCoursesByDepartment(departmentId);
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        return dto;
    }
   
    private Department convertToEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        return department;
    }

}
