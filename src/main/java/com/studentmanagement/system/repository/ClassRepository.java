package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.ClassEntity;
import com.studentmanagement.system.entity.CollegeLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.studentmanagement.system.entity.Teacher;
import com.studentmanagement.system.entity.Term;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    @Query("SELECT teacher FROM ClassEntity WHERE id= :classId")
    Optional<Teacher> getTeacherForClass(@Param("classId") Long classId);

    @Query("SELECT c FROM ClassEntity c WHERE id= :classId")
    Optional<ClassEntity> getClassById(@Param("classId") Long classId);

    List<ClassEntity> findByTermAndLevel(Term term, CollegeLevel level);

    @Query("SELECT c FROM ClassEntity c WHERE c.course.id= :courseId")
    List<ClassEntity> findByCourse(@Param("courseId") Long courseId);

    @Query("SELECT c FROM ClassEntity c WHERE c.teacher.id = :teacherId")
    List<ClassEntity> findByTeacher(@Param("teacherId") Long teacherId);

}
