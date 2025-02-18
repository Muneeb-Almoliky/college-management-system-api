package com.studentmanagement.system.controller;

import com.studentmanagement.system.dto.CollegeLevelDTO;
import com.studentmanagement.system.service.CollegeLevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/college-levels")
public class CollegeLevelController {

    private final CollegeLevelService collegeLevelService;

    public CollegeLevelController(CollegeLevelService collegeLevelService) {
        this.collegeLevelService = collegeLevelService;
    }

    @GetMapping
    public ResponseEntity<List<CollegeLevelDTO>> getAllCollegeLevels() {
        List<CollegeLevelDTO> levels = collegeLevelService.getAllCollegeLevels();
        return levels.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(levels);
    }

    @PostMapping
    public ResponseEntity<CollegeLevelDTO> createCollegeLevel(@RequestBody CollegeLevelDTO dto) {
        CollegeLevelDTO createdLevel = collegeLevelService.createCollegeLevel(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLevel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeLevelDTO> getCollegeLevelById(@PathVariable Long id) {
        return ResponseEntity.ok(collegeLevelService.getCollegeLevelById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollegeLevelDTO> updateCollegeLevel(@PathVariable Long id, @RequestBody CollegeLevelDTO dto) {
        CollegeLevelDTO updatedLevel = collegeLevelService.updateCollegeLevel(id, dto);
        return ResponseEntity.ok(updatedLevel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollegeLevel(@PathVariable Long id) {
        collegeLevelService.deleteCollegeLevel(id);
        return ResponseEntity.noContent().build();
    }
}
