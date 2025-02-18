package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.Term;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
    
   @Query("SELECT t FROM Term t WHERE t.year.id = :yearId ORDER BY t.id ASC")
    List<Term> findTermsByAcademicYearIdOrdered(@Param("yearId") Long yearId);

    default Optional<Term> findFirstTermOfYear(Long yearId) {
        List<Term> terms = findTermsByAcademicYearIdOrdered(yearId);
        return terms.isEmpty() ? Optional.empty() : Optional.of(terms.get(0));
    }
}
