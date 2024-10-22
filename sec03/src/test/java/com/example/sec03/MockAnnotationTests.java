package com.example.sec03;

import com.example.sec03.dao.ApplicationDao;
import com.example.sec03.models.CollegeStudent;
import com.example.sec03.models.StudentGrades;
import com.example.sec03.service.ApplicationService;
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
    CollegeStudent collegeStudent;

    @Autowired
    StudentGrades studentGrades;

    @InjectMocks
    ApplicationService applicationService;

    @Mock
    ApplicationDao applicationDao;

}
