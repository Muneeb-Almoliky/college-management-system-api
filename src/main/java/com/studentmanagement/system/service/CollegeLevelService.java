package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.CollegeLevelDTO;
import com.studentmanagement.system.entity.CollegeLevel;
import com.studentmanagement.system.exception.ResourceNotFoundException;
import com.studentmanagement.system.repository.CollegeLevelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CollegeLevelService {

    private CollegeLevelRepository collegeLevelRepository;

    public CollegeLevelService (CollegeLevelRepository collegeLevelRepository) {
        this.collegeLevelRepository = collegeLevelRepository;
    }

    public List<CollegeLevelDTO> getAllCollegeLevels() {
        return collegeLevelRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public CollegeLevelDTO createCollegeLevel(CollegeLevelDTO dto) {
        CollegeLevel entity = new CollegeLevel();
        entity.setLevelName(dto.getLevelName());
        entity.setLevelOrder(dto.getLevelOrder());
        CollegeLevel saved = collegeLevelRepository.save(entity);
        return convertToDTO(saved);
    }

    public CollegeLevelDTO getCollegeLevelById(Long id) {
        CollegeLevel collegeLevel = collegeLevelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Level not found"));

        return convertToDTO(collegeLevel);
    }

    public CollegeLevelDTO updateCollegeLevel(Long id, CollegeLevelDTO dto) {
        CollegeLevel collegeLevel = collegeLevelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Level not found"));

        collegeLevel.setLevelName(dto.getLevelName());
        collegeLevel = collegeLevelRepository.save(collegeLevel);

        return convertToDTO(collegeLevel);
    }

    public void deleteCollegeLevel(Long id) {
        collegeLevelRepository.deleteById(id);
    }

    public List<CollegeLevelDTO> getStudentCollegeLevels(Long studentId) {
        return collegeLevelRepository.findByStudentId(studentId).stream().map(this::convertToDTO).toList();
    }

    private CollegeLevelDTO convertToDTO(CollegeLevel entity) {
        CollegeLevelDTO dto = new CollegeLevelDTO();
        dto.setId(entity.getId());
        dto.setLevelName(entity.getLevelName());
        dto.setLevelOrder(entity.getLevelOrder());
        return dto;
    }

}
