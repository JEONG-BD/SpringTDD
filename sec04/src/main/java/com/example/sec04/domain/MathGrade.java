package com.example.sec04.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "math_grade")
public class MathGrade implements Grade{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="studnet_id")
    private int studentId;

    @Column(name= "grade")
    private double grade;

    public MathGrade() {

    }

    public MathGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public void setId() {
        this.id = id;
    }

    @Override
    public int getStudentId() {
        return studentId;
    }

    @Override
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public void setGrade(double grade) {
        this.grade = grade;
    }


    @Override
    public double getGrade() {
        return grade;
    }
}
