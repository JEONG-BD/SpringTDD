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
import org.springframework.boot.test.mock.mockito.MockBean;
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

    //@InjectMocks
    @Autowired
    ApplicationService applicationService;

    //@Mock
    @MockBean
    ApplicationDao applicationDao;


    @BeforeEach
    public void beforeEach(){
        studentOne.setFirstName("Spring");
        studentOne.setLastName("Test Driven Development");
        studentOne.setEmailAddress("SpringBoot@Test.com");
        studentOne.setStudentGrades(studentGrades);
        System.out.println("BeforeEach");
        System.out.println("studentOne = " + studentOne);
        System.out.println("BeforeEach");
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

    @DisplayName("Find Gpa")
    @Test
    public void assertEqualsTestFindGpa() throws Exception{
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults()))
                .thenReturn(88.31);
        assertEquals(88.31, applicationService.findGradePointAverage(studentOne.getStudentGrades().getMathGradeResults()));
        //when

        //then
    }

    @DisplayName("Not Null")
    @Test
    public void testAssertNotNull() throws Exception{
        //given
        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);
        assertNotNull(applicationService.checkNull(studentOne.getStudentGrades().getMathGradeResults()), "Object should not be null");

        //when

        //then
    }


    @DisplayName("Thrown runtime error")
    @Test
    public void thrownRuntimeError ()  throws Exception{
        //given
        CollegeStudent nullStudent = (CollegeStudent)applicationContext.getBean("collegeStudent");
        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        verify(applicationDao, times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multiple Stubbing")
    @Test
    public void stubbingConsecutiveCalls() {
        CollegeStudent nullStudent = (CollegeStudent) applicationContext.getBean("collegeStudent");
        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw exception second time");

        assertThrows(RuntimeException.class, () ->
                applicationService.checkNull(nullStudent));

        assertEquals("Do not throw exception second time", applicationService.checkNull(nullStudent));

        verify(applicationDao, times(2)).checkNull(nullStudent);
    }



}
