package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.CollegeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface CollegeLevelRepository extends JpaRepository<CollegeLevel, Long> {

    List<CollegeLevel> findByLevelOrder(Integer levelOrder);

   @Query("SELECT l FROM CollegeLevel l JOIN StudentLevelProgress slp ON slp.collegeLevel.id = l.id WHERE slp.student.id = :studentId")
    List<CollegeLevel> findByStudentId(@Param("studentId") Long studentId);

}

