package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.Student;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    
    // @Query("SELECT DISTINCT g.student FROM Grade g " +
    //        "JOIN g.classEntity ce " +
    //        "JOIN ce.course c " +
    //        "JOIN c.department d " +
    //        "WHERE d.id = :departmentId")
    // List<Student> findStudentsByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT s FROM Student s WHERE s.department.id = :departmentId")
    List<Student> findStudentsByDepartmentId(@Param("departmentId") Long departmentId);

    Optional<Student> getStudentById(Long id);

    // Get students by department
    List<Student> findByDepartmentId(Long departmentId);

    // Get students by department and level
    @Query("SELECT s FROM Student s JOIN StudentLevelProgress slp ON s.id = slp.student.id " +
           "WHERE s.department.id = :departmentId AND slp.collegeLevel.id = :levelId")
    List<Student> findByDepartmentAndLevel(Long departmentId, Long levelId);
}
