package com.example.sec04;

import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.repository.StudentDao;
import com.example.sec04.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    public void setUpDatabase(){
        jdbcTemplate.execute("insert into student(id, firstName, lastName, emailAddress) " +
                "values (1, 'Eric', 'Roby', 'eric.roby@test.com')" );
    }

    @Test
    public void createStudentService() throws Exception{
        //given
        studentService.createStudent("Chat", "Darby", "chad.darby@test.com");

        //when
        CollegeStudent student = studentDao.findByEmailAddress("chad.darby@test.com");
        //then
        Assertions.assertEquals("chad.darby@test.com", student.getEmailAddress(), "find by email");

    }

    @Test
    public void isStudentNullCheck() throws Exception{
        //given

        //when

        //then
        Assertions.assertTrue(studentService.checkIfStudentIsNull(1));
        Assertions.assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService() throws Exception{
        //given
        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);
        Assertions.assertTrue(deletedCollegeStudent.isPresent(), "Return True");
        //when
        studentService.deleteStudent(1);
        deletedCollegeStudent = studentDao.findById(1);
        //then
        Assertions.assertFalse(deletedCollegeStudent.isPresent(), "Return False");
    }

    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService() throws Exception{
        //given
        Iterable<CollegeStudent> iterableCollegeStudents = studentService.getGradeBook();

        //when
        List<CollegeStudent> collegeStudents = new ArrayList<>();
        //then
        for (CollegeStudent collegeStudent : iterableCollegeStudents) {
            collegeStudents.add(collegeStudent);
        }

        Assertions.assertEquals(5, collegeStudents.size());
    }

    @AfterEach
    public void setUpAfterTransaction(){
        jdbcTemplate.execute("DELETE FROM student");
    }
}
