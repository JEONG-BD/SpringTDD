package com.example.sec04;

import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.repository.StudentDao;
import com.example.sec04.service.StudentAndGradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTests {

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;
    @Test
    public void createStudentService() throws Exception{
        //given
        studentService.createStudent("Chat", "Darby", "chad.darby@test.com");

        //whenbb
        CollegeStudent studnet = studentDao.findByEmailAddress("chad.darby@test.com");
        //then
        Assertions.assertEquals("chad.darby@test.com", studnet.getEmailAddress(), "find by email");

    }
}
