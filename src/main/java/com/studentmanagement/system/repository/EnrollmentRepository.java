package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.ClassEntity;
import com.studentmanagement.system.entity.Enrollment;
import com.studentmanagement.system.entity.EnrollmentId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {


    List<Enrollment> findById_StudentId(long studentId);

    List<Enrollment> findById_ClassId(long classId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.classEntity.id = :classId")
    List<Enrollment> getAllGradesForClass(@Param("classId") long classId);

    @Query("SELECT e.classEntity FROM Enrollment e WHERE e.student.id = :studentId")
    List<ClassEntity> findClassesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT e.classEntity FROM Enrollment e WHERE e.student.id = :studentId AND e.classEntity.level.id = :levelId")
    List<ClassEntity> findClassesByStudentIdAndLevel(@Param("studentId") Long studentId, @Param("levelId") Long levelId);

    @Query("SELECT e.classEntity FROM Enrollment e WHERE e.student.id = :studentId AND e.classEntity.term.id = :termId")
    List<ClassEntity> findClassesByStudentIdAndTerm(@Param("studentId") Long studentId, @Param("termId") Long termId);

    @Query("SELECT e.classEntity FROM Enrollment e WHERE e.student.id = :studentId AND e.classEntity.level.id = :levelId AND e.classEntity.term.id = :termId")
    List<ClassEntity> findClassesByStudentIdAndLevelAndTerm(@Param("studentId") Long studentId, @Param("levelId") Long levelId, @Param("termId") Long termId);

}
