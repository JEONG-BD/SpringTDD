package com.example.sec03;

import com.example.sec03.models.CollegeStudent;
import com.example.sec03.models.StudentGrades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(classes = Sec03Application.class)
public class ReplectionTestUtilsTests {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void studentBeforeEach(){
        studentOne.setFirstName("Eric");
        studentOne.setLastName("Roby");
        studentOne.setEmailAddress("eric.roby@test.com");
        studentOne.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(studentOne, "id", 1);
        ReflectionTestUtils.setField(studentOne, "studentGrades", new StudentGrades((new ArrayList<>(Arrays.asList(
                100.0, 85.0, 76.50, 91.75)))));
    }


    @Test
    public void getPrivateField() throws Exception{
        //given
        Assertions.assertEquals(1, ReflectionTestUtils.getField(studentOne, "id"));
        //when

        //then
    }

    @Test
    public void invokePrivateMethod() throws Exception{
        //given
        Assertions.assertEquals("Eric 1", ReflectionTestUtils.invokeMethod(studentOne, "getFirstNameAndId"),
                "Fail private method not call");
        //when

        //then
    }
}
