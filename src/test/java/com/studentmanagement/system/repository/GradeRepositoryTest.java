package com.studentmanagement.system.repository;

import com.studentmanagement.system.entity.ClassEntity;
import com.studentmanagement.system.entity.Course;
import com.studentmanagement.system.entity.Enrollment;
import com.studentmanagement.system.entity.EnrollmentId;
import com.studentmanagement.system.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GradeRepositoryTest {

    @Autowired
    private EnrollmentRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @BeforeEach
    void setUp() {
        // Create a Student
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        studentRepository.save(student);

        // Create a ClassEntity
        ClassEntity classEntity = new ClassEntity();
        Course course = new Course();
        course.setName("math");
        course.setCredits(3);
        classEntity.setCourse(course);
        classRepository.save(classEntity);

        // Create a Grade
        Enrollment grade = new Enrollment();
        EnrollmentId gradeId = new EnrollmentId(student.getId(), classEntity.getId());
        grade.setId(gradeId);
        grade.setStudent(student);
        grade.setClassEntity(classEntity);
        grade.setScore(95.0);

        gradeRepository.save(grade);
    }

    @Test
    void testGetAllGradesForClass() {
        // Act
        List<Enrollment> grades = gradeRepository.getAllGradesForClass(1L); // Replace with the actual classId

        // Assert
        assertThat(grades).isNotEmpty();
        assertThat(grades.get(0).getScore()).isEqualTo(95.0);
    }
}

