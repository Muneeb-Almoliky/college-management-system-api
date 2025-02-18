package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.ClassDTO;
import com.studentmanagement.system.dto.TeacherDTO;
import com.studentmanagement.system.entity.Teacher;
import com.studentmanagement.system.exception.ResourceNotFoundException;
import com.studentmanagement.system.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;
    private ClassService classService;

    public TeacherService(TeacherRepository teacherRepository, ClassService classService) {
        this.teacherRepository = teacherRepository;
        this.classService = classService;
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public TeacherDTO getTeacherById(Long id) {
        Teacher foundTeacher = teacherRepository.findById(id).orElse(null);
        return convertToDTO(foundTeacher);
    }

    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setEmailAddress(teacherDTO.getEmailAddress());
        teacher = teacherRepository.save(teacher);
        return convertToDTO(teacher);
    }

    public TeacherDTO updateTeacher(Long id, TeacherDTO dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setEmailAddress(dto.getEmailAddress());
        teacher.setPhoneNumber(dto.getPhoneNumber());
        teacherRepository.save(teacher);

        return convertToDTO(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public List<ClassDTO> getClassesForTeacher(Long teacherId) {
        return classService.getClassesForTeacher(teacherId);
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
