package com.studentmanagement.system.controller;

import com.studentmanagement.system.dto.TermDTO;
import com.studentmanagement.system.service.TermService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
public class TermController {

    private final TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }

    @GetMapping
    public ResponseEntity<List<TermDTO>> getAllTerms() {
        List<TermDTO> terms = termService.getAllTerms();
        return terms.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(terms);
    }
    
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<TermDTO> createTerm(@RequestBody TermDTO termDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(termService.createTerm(termDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermDTO> getTermById(@PathVariable Long id) {
        TermDTO termDTO = termService.getTermById(id);
        return termDTO != null ? ResponseEntity.ok(termDTO) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TermDTO> updateTerm(@PathVariable Long id, @RequestBody TermDTO termDTO) {
        return ResponseEntity.ok(termService.updateTerm(id, termDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Long id) {
        termService.deleteTerm(id);
        return ResponseEntity.noContent().build();
    }
}
