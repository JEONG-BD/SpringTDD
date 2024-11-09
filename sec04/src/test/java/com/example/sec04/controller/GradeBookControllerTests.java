package com.example.sec04.controller;

import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.domain.GradeBookCollegesStudent;
import com.example.sec04.domain.MathGrade;
import com.example.sec04.repository.MathGradesDao;
import com.example.sec04.repository.StudentDao;
import com.example.sec04.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradeBookControllerTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentAndGradeService;

    @Autowired
    private StudentAndGradeService studentService;


    @Autowired
    private static MockHttpServletRequest request;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

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

    @BeforeAll
    public static void setUp(){
        request = new MockHttpServletRequest();
        request.setParameter("firstName", "Chad");
        request.setParameter("lastName", "Darby");
        request.setParameter("emailAddress", "chad.darby@test.com");

    }

    @BeforeEach
    public void beforeEach(){
//        jdbcTemplate.execute("insert into student(id, firstName, lastName, emailAddress) " +
//                "values (1, 'Eric', 'Roby', 'eric.roby@test.com')" );
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);

    }

    @Test
    public void getStudentsHttpRequest() throws Exception{
        //given
        CollegeStudent studentOne = new GradeBookCollegesStudent("Eric", "Roby", "eric.roby@test.com");
        CollegeStudent studentTwo = new GradeBookCollegesStudent("Chad", "Darby", "chad.darby@test.com");
        List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne, studentTwo));

        //when
        when(studentAndGradeService.getGradeBook()).thenReturn(collegeStudentList);

        //then
        Assertions.assertIterableEquals(collegeStudentList, studentAndGradeService.getGradeBook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav= mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");
    }


    @Test
    public void setCreateStudentHttpRequest() throws Exception{
        //given
        CollegeStudent studentOne = new CollegeStudent("eric", "roby", "eric_roby@test.com");
        List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne));

        when((studentAndGradeService.getGradeBook())).thenReturn(collegeStudentList);

        Assertions.assertIterableEquals(collegeStudentList, studentAndGradeService.getGradeBook());

        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", request.getParameterValues("firstName"))
                .param("lastName", request.getParameterValues("lastName"))
                .param("emailAddress", request.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();


        //when
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");

        CollegeStudent verifyStudent = studentDao.findByEmailAddress("chad.darby@test.com");


        //then
        Assertions.assertNotNull(verifyStudent, "Student should be found");
    }

    @Test
    public void deleteStudentHttpRequestTest() throws Exception{
        //given
        Assertions.assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete/student/{id}", 1))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");

        Assertions.assertFalse(studentDao.findById(1).isPresent());

    }

    @Test
    public void deleteStudentHttpRequestErrorPage() throws Exception{
        //given
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/delete/student/{id}", 0))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "error");

        //when

        //then
    }

    @Test
    public void studentInformationHttpRequest() throws Exception{
        //given

        Assertions.assertTrue(studentDao.findById(1).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 1))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "studentInformation");
        //when

        //then
    }

    @Test
    public void studentInformationHttpStudentDoesNotExistRequest() throws Exception{
        //given
        Assertions.assertFalse(studentDao.findById(0).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 0))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "error");
        //when

        //then
    }
    @Test
    public void createValidGradeHttpRequestTest() throws Exception{
        //given
        Assertions.assertTrue(studentDao.findById(1).isPresent());

        GradeBookCollegesStudent student = studentService.studentInformation(1);

        Assertions.assertEquals(1, student.getStudentGrades().getMathGradeResults().size());
        //then
        MvcResult mvcResult = this.mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("gradeType", "math")
                .param("studentId", "1")).andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "studentInformation");

        student = studentService.studentInformation(1);

        Assertions.assertEquals(2, student.getStudentGrades().getMathGradeResults().size());
    }

    @Test
    public void createValidGradeHttpRequestStudentDoesNotExistEmptyResponseTest() throws Exception{
        //given
        MvcResult mvcResult = this.mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("gradeType", "history")
                .param("studentId", "0"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "error");
        //when

        //then
    }

    @Test
    public void cereateNonValidGradeHttpRequestGradeTypeDoesNotExistEmptyResponse() throws Exception{
        //given
        Assertions.assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult = this.mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("gradeType", "literature")
                .param("studentId", "1"))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "error");
        //when

        //then
    }

    @Test
    public void deleteAValidGradeHttpRequestTest() throws Exception{
        //given
        Optional<MathGrade> mathGrade = mathGradesDao.findById(1);
        //when
        Assertions.assertTrue(mathGrade.isPresent());

        //then
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/grades/{id}/{gradeType}", 1 ,"math"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "studentInformation");

        mathGrade = mathGradesDao.findById(2);

        Assertions.assertFalse(mathGrade.isPresent());
    }


    @Test
    public void deleteInvalidGradeHttpRequestTest() throws Exception{
        //given
        Optional<MathGrade> mathGrade = mathGradesDao.findById(2);

        Assertions.assertFalse(mathGrade.isPresent());
        //when
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/grades/{id}/{gradeType}", 2,"math"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");


        //then
    }
    @Test
    public void deleteInvalidGradeSubjectHttpRequestTest() throws Exception{
        //given
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/grades/{id}/{gradeType}", 1, "literature"))
                .andExpect(status().isOk()).andReturn();
        //when
        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
        //then
    }
    
    @AfterEach
    public void afterEach(){

        //jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);

    }
}
