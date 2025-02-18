package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.StudentLevelProgress;
import com.studentmanagement.system.entity.StudentLevelProgressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentLevelProgressRepository extends JpaRepository<StudentLevelProgress, StudentLevelProgressId> {


    List<StudentLevelProgress> findById_StudentId(Long studentId);

    List<StudentLevelProgress> findById_YearId(Long yearId);

    List<StudentLevelProgress> findById_LevelId(Long levelId);

    @Query("SELECT slp FROM StudentLevelProgress slp WHERE slp.id.studentId = :studentId")
    List<StudentLevelProgress> findByStudentId(Long studentId);    

}
