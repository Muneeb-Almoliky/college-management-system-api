package com.studentmanagement.system.controller;

import com.studentmanagement.system.dto.CollegeYearDTO;
import com.studentmanagement.system.service.CollegeYearService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/college-years")
public class CollegeYearController {

    private final CollegeYearService collegeYearService;

    public CollegeYearController(CollegeYearService collegeYearService) {
        this.collegeYearService = collegeYearService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<CollegeYearDTO> createCollegeYear(@RequestBody CollegeYearDTO dto) {
        CollegeYearDTO createdYear = collegeYearService.createNewAcademicYear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdYear);
    }

    @GetMapping
    public ResponseEntity<List<CollegeYearDTO>> getAllCollegeYears() {
        List<CollegeYearDTO> years = collegeYearService.getAllCollegeYears();
        return years.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(years);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeYearDTO> getYearById(@PathVariable Long id) {
        return ResponseEntity.ok(collegeYearService.getAcademicYearDetails(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollegeYearDTO> updateYear(@PathVariable Long id, @RequestBody CollegeYearDTO dto) {
        CollegeYearDTO updatedYear = collegeYearService.updateAcademicYear(id, dto);
        return ResponseEntity.ok(updatedYear);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteYear(@PathVariable Long id) {
        collegeYearService.deleteAcademicYear(id);
        return ResponseEntity.noContent().build();
    }

    // @PostMapping("/{id}/progress")
    // public ResponseEntity<String> progressStudentsToNewYear(@PathVariable Long id) {
    //     collegeYearService.progressStudentsToNewYear(id);
    //     return ResponseEntity.ok("Students have been successfully progressed to the new academic year.");
    // }
}
