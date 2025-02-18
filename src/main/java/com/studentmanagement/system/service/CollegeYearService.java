package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.CollegeYearDTO;
import com.studentmanagement.system.entity.CollegeYear;
import com.studentmanagement.system.repository.CollegeYearRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CollegeYearService {

    private CollegeYearRepository collegeYearRepository;
    private StudentLevelProgressService progressionService;

    public CollegeYearService(CollegeYearRepository collegeYearRepository, StudentLevelProgressService progressionService) {
        this.collegeYearRepository = collegeYearRepository;
        this.progressionService = progressionService;
    }

    public List<CollegeYearDTO> getAllCollegeYears() {
        return collegeYearRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public List<CollegeYearDTO> getStudentCollegeYears(Long studentId) {
        return collegeYearRepository.findStudentYears(studentId).stream().map(this::convertToDTO).toList();
    }

    public CollegeYearDTO createCollegeYear(CollegeYearDTO dto) {
        CollegeYear entity = new CollegeYear();
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        CollegeYear saved = collegeYearRepository.save(entity);
        return convertToDTO(saved);
    }

    @Transactional
    public CollegeYearDTO createNewAcademicYear(CollegeYearDTO dto) {
        // Save new year (initially inactive)
        CollegeYear entity = new CollegeYear();

        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setActive(false);

        CollegeYear saved = collegeYearRepository.save(entity);
        // Trigger progression and enrollment
        progressionService.progressStudentsToNewYear(saved.getId());

        return convertToDTO(saved);
    }

    public CollegeYearDTO getAcademicYearDetails(Long id) {
        CollegeYear collegeYear = collegeYearRepository.getReferenceById(id);
        return this.convertToDTO(collegeYear);
    }

    public CollegeYearDTO updateAcademicYear(Long id, CollegeYearDTO dto) {
        CollegeYear collegeYear = collegeYearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Year not found"));
        collegeYear.setStartDate(dto.getStartDate());
        collegeYear.setEndDate(dto.getEndDate());
        collegeYear.setActive(dto.isActive());
        collegeYear = collegeYearRepository.save(collegeYear);

        return this.convertToDTO(collegeYear);
    }

    public void deleteAcademicYear(Long id) {
        collegeYearRepository.deleteById(id);
    }

    private CollegeYearDTO convertToDTO(CollegeYear entity) {
        CollegeYearDTO dto = new CollegeYearDTO();
        dto.setId(entity.getId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

}
