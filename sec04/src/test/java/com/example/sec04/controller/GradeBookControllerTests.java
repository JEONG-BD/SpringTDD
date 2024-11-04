package com.example.sec04.controller;

import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.domain.GradeBookCollegesStudent;
import com.example.sec04.domain.MathGrade;
import com.example.sec04.repository.StudentDao;
import com.example.sec04.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static MockHttpServletRequest request;

    @Autowired
    private StudentDao studentDao;

    @BeforeAll
    public static void setUp(){
        request = new MockHttpServletRequest();
        request.setParameter("firstName", "Chad");
        request.setParameter("lastName", "Darby");
        request.setParameter("emailAddress", "chad.darby@test.com");

    }

    @BeforeEach
    public void beforeEach(){
        jdbcTemplate.execute("insert into student(id, firstName, lastName, emailAddress) " +
                "values (1, 'Eric', 'Roby', 'eric.roby@test.com')" );
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
    public void createGradeServiceTest() throws Exception{
        //given

        Assertions.assertTrue(studentAndGradeService.createGrade(80.50, 1, "math"));

        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(1);
        //when
        Assertions.assertTrue(mathGrades.iterator().hasNext(), "Student has math grades");

        //then
    }

    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("DELETE FROM student");
    }
}
