package com.example.sec04;

import com.example.sec04.domain.CollegeStudent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTests {

    @Test
    public void createStudentService() throws Exception{
        //given
        //studentService.createStudend("Chat", "Darby", "chad.darby@test.com");
        //when
        //CollegeStudent studnet = studentDao.findByEmailAddress("chad.darby@test.com");
        //then
        //Assertions.assertEquals("chad.darby@test.com", studnet.getEmailAddress(), "find by email");

    }
}
