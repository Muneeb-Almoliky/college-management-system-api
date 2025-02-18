package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.CollegeYear;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CollegeYearRepository extends JpaRepository<CollegeYear, Long> {

    @Query("SELECT y FROM CollegeYear y JOIN StudentLevelProgress slp ON y.id=slp.collegeYear.id WHERE slp.student.id = :studentId")
    List<CollegeYear> findStudentYears(@Param("studentId") Long studentId);

    @Query("SELECT y FROM CollegeYear y WHERE y.isActive = true")
    Optional<CollegeYear> findByIsActiveTrue();

    // Find most recent inactive year
    @Query("SELECT y FROM CollegeYear y WHERE y.isActive = false ORDER BY y.id DESC LIMIT 1")
    Optional<CollegeYear> findByIsActiveFalse();

    @Modifying
    @Query("UPDATE CollegeYear y SET y.isActive = false")
    void deactivateAllYears();

    // Find the ID of the previously active year
    @Query("SELECT y.id FROM CollegeYear y WHERE y.isActive = true")
    Long findPreviousActiveYearId();

}
