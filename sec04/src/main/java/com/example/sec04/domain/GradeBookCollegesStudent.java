package com.example.sec04.domain;

public class GradeBookCollegesStudent extends CollegeStudent {

    private int id;

    private StudentGrades studentGrades;

    public GradeBookCollegesStudent(String firstName, String lastName, String emailAddress) {
        super(firstName, lastName, emailAddress);
    }

    public GradeBookCollegesStudent(int id, String firstName, String lastName, String emailAddress, StudentGrades studentGrades) {
        super(firstName, lastName, emailAddress);
        this.studentGrades = studentGrades;
        this.id = id;
    }

    public StudentGrades getStudentGrades(){
        return studentGrades;
    }

    public void setStudentGrades(StudentGrades studentGrades){
        this.studentGrades = studentGrades;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
