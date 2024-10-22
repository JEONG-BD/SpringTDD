package com.example.sec03;

import com.example.sec03.dao.ApplicationDao;
import com.example.sec03.models.CollegeStudent;
import com.example.sec03.models.StudentGrades;
import com.example.sec03.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

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

}
