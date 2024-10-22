package com.example.sec03;

import com.example.sec03.dao.ApplicationDao;
import com.example.sec03.models.CollegeStudent;
import com.example.sec03.models.StudentGrades;
import com.example.sec03.service.ApplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Sec03Application.class)
public class MockAnnotationTests {


    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @InjectMocks
    ApplicationService applicationService;

    @Mock
    ApplicationDao applicationDao;


    @BeforeEach
    public void beforeEach(){
        studentOne.setFirstName("Spring");
        studentOne.setLastName("Test Driven Development");
        studentOne.setEmailAddress("SpringBoot@Test.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("When & " + "Verify")
    @Test
    public void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults())).thenReturn(100.00);
        assertEquals(100, applicationService.addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()));
//        assertNotEquals(300, applicationService.addGradeResultsForSingleClass(
//                studentOne.getStudentGrades().getMathGradeResults()));

        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
        verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

}
