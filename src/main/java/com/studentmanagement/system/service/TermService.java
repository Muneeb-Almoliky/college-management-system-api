package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.TermDTO;
import com.studentmanagement.system.entity.CollegeYear;
import com.studentmanagement.system.entity.Term;
import com.studentmanagement.system.exception.ResourceNotFoundException;
import com.studentmanagement.system.repository.CollegeYearRepository;
import com.studentmanagement.system.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TermService {

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private CollegeYearRepository collegeYearRepository;

    public List<TermDTO> getAllTerms() {
        return termRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public TermDTO createTerm(TermDTO termDTO) {
        Term term = convertToEntity(termDTO);
        Term savedTerm = termRepository.save(term);
        return convertToDTO(savedTerm);
    }

    public TermDTO getTermById(Long id) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Term not found"));
                
        return this.convertToDTO(term);
    }

    public TermDTO updateTerm(Long id, TermDTO termDTO) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Term not found with id: " + id));
        CollegeYear collegeYear = collegeYearRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Year not found."));
        term.setName(termDTO.getName());
        term.setYear(collegeYear);
        term.setStartDate(termDTO.getStartDate());
        term.setEndDate(termDTO.getEndDate());
        Term updatedTerm = termRepository.save(term);
        return convertToDTO(updatedTerm);
    }

    public void deleteTerm(Long id) {
        termRepository.deleteById(id);
    }

    private TermDTO convertToDTO(Term term) {
        TermDTO dto = new TermDTO();
        dto.setId(term.getId());
        dto.setName(term.getName());
        dto.setYear(term.getYear().getId());
        dto.setStartDate(term.getStartDate());
        dto.setEndDate(term.getEndDate());
        return dto;
    }

    private Term convertToEntity(TermDTO dto) {
        Term term = new Term();
        CollegeYear collegeYear = collegeYearRepository.findById(dto.getYear()).orElseThrow(() -> new ResourceNotFoundException("Year"));
        term.setId(dto.getId());
        term.setName(dto.getName());
        term.setYear(collegeYear);
        term.setStartDate(dto.getStartDate());
        term.setEndDate(dto.getEndDate());
        return term;
    }
}
