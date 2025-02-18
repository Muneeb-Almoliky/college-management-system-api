package com.studentmanagement.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentLevelProgressId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "year_id")
    private Long yearId;

    public StudentLevelProgressId() {}

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public StudentLevelProgressId(Long studentId, Long levelId, Long yearId) {
        this.studentId = studentId;
        this.levelId = levelId;
        this.yearId = yearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentLevelProgressId that = (StudentLevelProgressId) o;

        if (!studentId.equals(that.studentId)) return false;
        if (!levelId.equals(that.levelId)) return false;
        return yearId.equals(that.yearId);
    }

    @Override
    public int hashCode() {
        int result = studentId.hashCode();
        result = 31 * result + levelId.hashCode();
        result = 31 * result + yearId.hashCode();
        return result;
    }
}
