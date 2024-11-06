package com.example.sec04;

import com.example.sec04.domain.*;
import com.example.sec04.repository.HistoryGradesDao;
import com.example.sec04.repository.MathGradesDao;
import com.example.sec04.repository.ScienceGradesDao;
import com.example.sec04.repository.StudentDao;
import com.example.sec04.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;


    @BeforeEach
    public void setUpDatabase(){
   /*     jdbcTemplate.execute("insert into student(id, firstName, lastName, emailAddress) " +
                "values (1, 'Eric', 'Roby', 'eric.roby@test.com')" );

        jdbcTemplate.execute("insert into math_grade(id, student_id, grade) " +
                "values (1, 1, 100.0)");
        jdbcTemplate.execute("insert into history_grade(id, student_id, grade) " +
                "values (1, 1, 100.0)");
        jdbcTemplate.execute("insert into science_grade(id, student_id, grade) " +
                "values (1, 1, 100.0)");
    */
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);

    }

    @Test
    public void createStudentService() throws Exception{
        //given
        studentService.createStudent("Chat", "Darby", "chad.darby@test.com");

        //when
        CollegeStudent student = studentDao.findByEmailAddress("chad.darby@test.com");
        //then
        assertEquals("chad.darby@test.com", student.getEmailAddress(), "find by email");

    }

    @Test
    public void isStudentNullCheck() throws Exception{
        //given

        //when

        //then
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService() throws Exception{
        //given
        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);
        Optional<MathGrade> deleteMathGrade = mathGradesDao.findById(1);
        Optional<HistoryGrade> deleteHistoryGrade = historyGradesDao.findById(1);
        Optional<ScienceGrade> deleteScienceGrade = scienceGradesDao.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(), "Return True");
        assertTrue(deleteMathGrade.isPresent(), "Return Math");
        assertTrue(deleteHistoryGrade.isPresent(), "Return History");
        assertTrue(deleteScienceGrade.isPresent(), "Return Science");

        //when
        studentService.deleteStudent(1);

        deletedCollegeStudent = studentDao.findById(1);
        deleteMathGrade = mathGradesDao.findById(1);
        deleteHistoryGrade = historyGradesDao.findById(1);
        deleteScienceGrade = scienceGradesDao.findById(1);

        //then
        assertFalse(deletedCollegeStudent.isPresent(), "Return False");
        assertFalse(deleteMathGrade.isPresent());
        assertFalse(deleteHistoryGrade.isPresent());
        assertFalse(deleteScienceGrade.isPresent());

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

        assertEquals(5, collegeStudents.size());
    }


    @Test
    public void createGradeServiceTest() throws Exception{
        //given
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(80.50, 1, "science"));
        assertTrue(studentService.createGrade(80.50, 1, "history"));

        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);

        //when
        //Assertions.assertTrue(mathGrades.iterator().hasNext(), "Student has math grades");
        assertTrue(((Collection<MathGrade>)mathGrades).size()==2, "Student has math grades" );
        //Assertions.assertTrue(scienceGrades.iterator().hasNext(), "Student has science grades");
        assertTrue(((Collection<ScienceGrade>)scienceGrades).size()==2, "Student has science grades");
        //Assertions.assertTrue(historyGrades.iterator().hasNext(), "Student has history grades");
        assertTrue(((Collection<HistoryGrade>)historyGrades).size()==2, "Student has history grades" );

        //then
    }

    @Test
    public void createGradeServiceReturnFalseTest() throws Exception{
        //given
        assertFalse(studentService.createGrade(105, 1, "math"));
        assertFalse(studentService.createGrade(-5, 1, " math"));
        assertFalse(studentService.createGrade(80.50, 2, " math"));
        assertFalse(studentService.createGrade(80.50, 1, " literature"));

        //when

        //then
    }

    @Test
    public void deleteGradeServiceTest() throws Exception{
        //given
        assertEquals(1, studentService.deleteGrade(1, "math"),
                "Return student id after delete");
        //when
        assertEquals(1, studentService.deleteGrade(1, "science"),
                "Return student id after delete");

        assertEquals(1, studentService.deleteGrade(1, "history"),
                "Return student id after delete");
        //then
    }

    @Test
    public void deleteGradeServiceReturnFailTest() throws Exception{
        //given
        assertEquals(0, studentService.deleteGrade(0, "science"), "No Student should have 0 id");
        assertEquals(0, studentService.deleteGrade(0, "literature"),"No Student should have 0 id");
        //when

        //then
    }

    @Test
    public void studentInformation() throws Exception{
        //given
        GradeBookCollegesStudent gradeBookCollegesStudent = studentService.studentInformation(1);
        //when
        assertNotNull(gradeBookCollegesStudent);
        assertEquals(1, gradeBookCollegesStudent.getId());
        assertEquals("Eric", gradeBookCollegesStudent.getFirstName());
        assertEquals("Roby", gradeBookCollegesStudent.getLastName());
        assertEquals("eric.roby@test.com", gradeBookCollegesStudent.getEmailAddress());
        assertTrue(gradeBookCollegesStudent.getStudentGrades().getMathGradeResults().size() == 1 );
        assertTrue(gradeBookCollegesStudent.getStudentGrades().getHistoryGradeResults().size() == 1 );
        assertTrue(gradeBookCollegesStudent.getStudentGrades().getScienceGradeResults().size() == 1 );

        //then
    }

    @Test
    public void studentInformationServiceReturnNull() throws Exception{
        //given
        GradeBookCollegesStudent gradeBookCollegesStudent = studentService.studentInformation(0);

        assertNull(gradeBookCollegesStudent);
        //when

        //then
    }

    @AfterEach
    public void setUpAfterTransaction() {
        /*
        jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute("DELETE FROM math_grade");
        jdbcTemplate.execute("DELETE FROM history_grade");
        jdbcTemplate.execute("DELETE FROM science_grade");
        */
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);

    }
}
