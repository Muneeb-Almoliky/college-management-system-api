package com.studentmanagement.system.controller;

import com.studentmanagement.system.dto.StudentLevelProgressDTO;
import com.studentmanagement.system.service.StudentLevelProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/student-year-levels")
@PreAuthorize("hasRole('ADMIN')")
public class StudentLevelProgressController {

    @Autowired
    private StudentLevelProgressService studentYearLevelService;

    @GetMapping
    public List<StudentLevelProgressDTO> getAllStudentYearLevels() {
        return studentYearLevelService.getAllStudentYearLevels();
    }

    @GetMapping("/student/{studentId}")
    public List<StudentLevelProgressDTO> getStudentYearLevelsByStudent(@PathVariable Long studentId) {
        return studentYearLevelService.getStudentYearLevelsByStudent(studentId);
    }

    @PostMapping
    public StudentLevelProgressDTO assignStudentToYearLevel(@RequestBody StudentLevelProgressDTO dto) {
        return studentYearLevelService.assignStudentToYearLevel(dto);
    }
}

