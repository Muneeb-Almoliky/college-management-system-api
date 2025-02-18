package com.studentmanagement.system.service;

import com.studentmanagement.system.dto.StudentLevelProgressDTO;
import com.studentmanagement.system.entity.ClassEntity;
import com.studentmanagement.system.entity.CollegeLevel;
import com.studentmanagement.system.entity.CollegeYear;
import com.studentmanagement.system.entity.Enrollment;
import com.studentmanagement.system.entity.Student;
import com.studentmanagement.system.entity.StudentLevelProgress;
import com.studentmanagement.system.entity.StudentLevelProgressId;
import com.studentmanagement.system.entity.Term;
import com.studentmanagement.system.exception.ResourceNotFoundException;
import com.studentmanagement.system.repository.ClassRepository;
import com.studentmanagement.system.repository.CollegeYearRepository;
import com.studentmanagement.system.repository.EnrollmentRepository;
import com.studentmanagement.system.repository.StudentLevelProgressRepository;
import com.studentmanagement.system.repository.StudentRepository;
import com.studentmanagement.system.repository.TermRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentLevelProgressService {

    private StudentLevelProgressRepository studentYearLevelRepository;
    private CollegeYearRepository collegeYearRepo;
    private StudentLevelProgressRepository studentYearLevelRepo;
    private ClassRepository classRepo;
    private StudentRepository studentRepo;
    private EnrollmentRepository studentClassRepo;
    private TermRepository termRepo;

    public StudentLevelProgressService(
            StudentLevelProgressRepository studentYearLevelRepository,
            CollegeYearRepository collegeYearRepo,
            StudentLevelProgressRepository studentYearLevelRepo,
            ClassRepository classRepo,
            StudentRepository studentRepo,
            EnrollmentRepository studentClassRepo,
            TermRepository termRepo) {

        this.studentYearLevelRepository = studentYearLevelRepository;
        this.collegeYearRepo = collegeYearRepo;
        this.studentYearLevelRepo = studentYearLevelRepo;
        this.classRepo = classRepo;
        this.studentRepo = studentRepo;
        this.studentClassRepo = studentClassRepo;
        this.termRepo = termRepo;
    }

    public List<StudentLevelProgressDTO> getAllStudentYearLevels() {
        return studentYearLevelRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public List<StudentLevelProgressDTO> getStudentYearLevelsByStudent(Long studentId) {
        return studentYearLevelRepository.findById_StudentId(studentId).stream().map(this::convertToDTO).toList();
    }

    public StudentLevelProgressDTO assignStudentToYearLevel(StudentLevelProgressDTO dto) {
        StudentLevelProgressId id = new StudentLevelProgressId();
        id.setStudentId(dto.getStudentId());
        id.setLevelId(dto.getLevelId());
        id.setYearId(dto.getYearId());

        StudentLevelProgress entity = new StudentLevelProgress();
        entity.setId(id);

        StudentLevelProgress saved = studentYearLevelRepository.save(entity);
        return convertToDTO(saved);
    }

   
    @Transactional
    public void progressStudentsToNewYear(Long newYearId) {
        CollegeYear newYear = collegeYearRepo.findById(newYearId)
                .orElseThrow(() -> new ResourceNotFoundException("Year not found"));

        Long previousYearId = collegeYearRepo.findPreviousActiveYearId();

        // Activate new year and deactivate old one
        collegeYearRepo.deactivateAllYears();
        newYear.setActive(true);
        collegeYearRepo.save(newYear);

        createTermsForYear(newYear);

        // Get the first term of the new year
        Term firstTerm = termRepo.findFirstTermOfYear(newYearId)
                .orElseThrow(() -> new ResourceNotFoundException("No terms defined for the new year"));

        List<StudentLevelProgress> currentEnrollments = studentYearLevelRepo
                .findById_YearId(previousYearId);

        List<Student> studentsToSave = new ArrayList<>();
        List<StudentLevelProgress> newEnrollments = new ArrayList<>();

        for (StudentLevelProgress enrollment : currentEnrollments) {
            Student student = enrollment.getStudent();
            CollegeLevel nextLevel = student.getCurrentLevel().getNextLevel();

            if (nextLevel != null) {
                student.setCurrentLevel(nextLevel);
                studentsToSave.add(student);

                StudentLevelProgress newEnrollment = new StudentLevelProgress();
                newEnrollment.setId(new StudentLevelProgressId(student.getId(), nextLevel.getId(), newYear.getId()));
                newEnrollment.setStudent(student);
                newEnrollment.setCollegeLevel(nextLevel);
                newEnrollment.setCollegeYear(newYear);
                studentYearLevelRepo.save(newEnrollment);

                // Enroll in the first term's classes for the new level
                autoEnrollInTermClasses(student, firstTerm, nextLevel);
            } else {
                student.setGraduated(true);
                studentsToSave.add(student);
            }
        }

        studentRepo.saveAll(studentsToSave); // Save all students at once
        studentYearLevelRepo.saveAll(newEnrollments); // Save all enrollments at once
    }

    private void createTermsForYear(CollegeYear year) {
        LocalDate yearStart = year.getStartDate();
        LocalDate yearEnd = year.getEndDate();

        Term fallTerm = new Term();
        fallTerm.setName("Fall " + yearStart.getYear());
        fallTerm.setStartDate(yearStart);
        fallTerm.setEndDate(yearStart.plusMonths(3).minusDays(1));
        fallTerm.setYear(year);
        termRepo.save(fallTerm);

        Term springTerm = new Term();
        springTerm.setName("Spring " + yearEnd.getYear());
        springTerm.setStartDate(yearStart.plusMonths(4));
        springTerm.setEndDate(yearEnd);
        springTerm.setYear(year);
        termRepo.save(springTerm);
    }

    private void autoEnrollInTermClasses(Student student, Term term, CollegeLevel level) {
        List<ClassEntity> classes = classRepo.findByTermAndLevel(term, level);

        classes.stream().forEach(clazz -> {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setClassEntity(clazz);
            studentClassRepo.save(enrollment);
        });
    }

    private StudentLevelProgressDTO convertToDTO(StudentLevelProgress entity) {
        StudentLevelProgressDTO dto = new StudentLevelProgressDTO();
        dto.setStudentId(entity.getStudent().getId());
        dto.setStudentName(entity.getStudent().getFirstName() + " " + entity.getStudent().getLastName());
        dto.setLevelId(entity.getCollegeLevel().getId());
        dto.setLevelName(entity.getCollegeLevel().getLevelName());
        dto.setYearId(entity.getCollegeYear().getId());
        dto.setYearStartDate(entity.getCollegeYear().getStartDate());
        dto.setYearEndDate(entity.getCollegeYear().getEndDate());
        return dto;
    }


}
