package com.example.sec04.service;

import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.domain.MathGrade;
import com.example.sec04.repository.MathGradesDao;
import com.example.sec04.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {


    @Autowired
    private StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    private MathGradesDao mathGradesDao;

    public void createStudent(String firstName, String lastName, String emailAddress){
        CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int studentId){
        Optional<CollegeStudent> student = studentDao.findById(studentId);

        if (student.isPresent()){
            return true;
        }
        return false;
    }

    public void deleteStudent(int id){
        if(checkIfStudentIsNull(id)){
            studentDao.deleteById(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook(){
        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        return collegeStudents;
    }

    public boolean createGrade(double grade, int studentId, String gradeTye){

        if(!checkIfStudentIsNull(studentId)){
            return false;
        }

        if(grade >= 0 && grade <=100){
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

            if (gradeTye.equals("math")){

                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);
                return true;
            }
        }

        return false;

    }
}
