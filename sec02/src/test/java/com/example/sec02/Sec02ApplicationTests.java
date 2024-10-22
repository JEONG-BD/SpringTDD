package com.example.sec02;

import com.example.sec02.models.CollegeStudent;
import com.example.sec02.models.StudentGrades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

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
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

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
    @DisplayName("Add grade results for student grades not equal")
    @Test
    public void addGradeResultForStudentGradesAssertNotEquals() throws Exception{
        //given
        Assertions.assertNotEquals(363.25, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMatchGradeResults()
        ));
        //when

        //then
    }

    @DisplayName("Is grade greater")
    @Test
    public void isGradeGreaterStudentGrades() throws Exception{
        //given
        Assertions.assertTrue(studentGrades.isGradeGreater(90, 75), "failure - should be true ");
        //when

        //then
    }

    @DisplayName("Is grade greater false")
    @Test
    public void isGradeGreaterStudentGreadesAssertFalse() throws Exception{
        //given
        Assertions.assertFalse(studentGrades.isGradeGreater(89, 92), "failure - should be false ");
        //when

        //then
    }


    @DisplayName("Check Null for student grades")
    @Test
    public void checkNullForStudentGrades() throws Exception{
        //given
        Assertions.assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMatchGradeResults()),
                "object should not be null");
        //when

        //then
    }

    @DisplayName("Create student without grade init")
    @Test
    public void createStudentWithoutGradesInit() throws Exception{
        //given
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstName("Chad");
        studentTwo.setLastName("Darby");
        studentTwo.setEmailAddress("chad.darby@test.com");
        Assertions.assertNotNull(studentTwo.getFirstName());
        Assertions.assertNotNull(studentTwo.getLastName());
        Assertions.assertNotNull(studentTwo.getEmailAddress());
        Assertions.assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));

        //when

        //then
    }

    @DisplayName("Verify students are prototypes")
    @Test
    public void verifyStudentsAreStudent() throws Exception{
        //given
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        Assertions.assertNotSame(student, studentTwo);
        //when

        //then
    }


    @DisplayName("Find Grade Point Average")
    @Test
    public void findGradePointAverage() throws Exception{
        //given
        Assertions.assertAll("Testing all assertEquals ",
                () -> Assertions.assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMatchGradeResults())),
                () -> Assertions.assertEquals(88.31, studentGrades.findGradePointAverage(
                        student.getStudentGrades().getMatchGradeResults()))
        );
        //when

        //then
    }

}
