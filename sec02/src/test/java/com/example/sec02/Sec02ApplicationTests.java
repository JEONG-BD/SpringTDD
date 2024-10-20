package com.example.sec02;

import com.example.sec02.models.ColleageStudent;
import com.example.sec02.models.StudentGrades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
class Sec02ApplicationTests {


    private static int count = 0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;


    @Value("${info.school.name}")
    private String schoolName;

    @Autowired
    ColleageStudent student;

    @Autowired
    StudentGrades studentGrades;


    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void beforeEach () throws Exception{
        //given
        count = count + 1;
        System.out.println("Testing : " + appInfo + " which is " + appDescription +
                " Version " + appVersion + " Execution of test method " + count);

        student.setFirstName("Eric");
        student.setLastName("Roby");
        student.setEmailAddress("eric.roby@test.com");
        studentGrades.setMatchGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
        student.setStudentGrades(studentGrades);
    }

    @DisplayName("Add grade results for student grades")
    @Test
    public void addGradeResultForStudentGrades() throws Exception{
        //given
        Assertions.assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMatchGradeResults()
        ));
        //when

        //then
    }

}
