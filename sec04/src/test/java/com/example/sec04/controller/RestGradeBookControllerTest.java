package com.example.sec04.controller;

import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.domain.MathGrade;
import com.example.sec04.repository.HistoryGradesDao;
import com.example.sec04.repository.MathGradesDao;
import com.example.sec04.repository.ScienceGradesDao;
import com.example.sec04.repository.StudentDao;
import com.example.sec04.service.StudentAndGradeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class RestGradeBookControllerTest {

    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    StudentAndGradeService studentCreateServiceMock;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private CollegeStudent student;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;


    @BeforeAll
    public static void setup() {

        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Chad");
        request.setParameter("lastname", "Darby");
        request.setParameter("emailAddress", "chad.darby@luv2code_school3.com");
    }

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    @Test
    public void getStudentsHttpRequest() throws Exception {

        student.setFirstName("Chad");
        student.setLastName("Darby");
        student.setEmailAddress("chad.darby@luv2code_school.com");
        entityManager.persist(student);
        entityManager.flush();

        mockMvc.perform(MockMvcRequestBuilders.get("/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    public void createStudentHttpRequestTest() throws Exception{
        //given
        student.setFirstName("Chad");
        student.setLastName("Darby");
        student.setEmailAddress("1234@luv2code_school4.com");

        mockMvc.perform(post("/api")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        CollegeStudent verifyStudent = studentDao.findByEmailAddress("1234@luv2Code_school4.com");
        assertNotNull(verifyStudent, "Student should be valid");


        //when

        //then
    }


    @Test
    public void deleteStudnetHttpRequestTest() throws Exception{
        //given
        assertTrue(studentDao.findById(1).isPresent());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));

        assertFalse(studentDao.findById(1).isPresent());

        //when
        //then
    }

    @Test
    public void deleteStudnetHttpRequestErrorPage() throws Exception{
        //given
        assertFalse(studentDao.findById(0).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/{id}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade wor not found")));
        //when

        //then
    }

    @Test
    public void studentInformationHttpRequest() throws Exception{
        //given
        Optional<CollegeStudent> student = studentDao.findById(1);

        assertTrue(student.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/studentInformation/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Eric")))
                .andExpect(jsonPath("$.lastName", is("Roby")))
                .andExpect(jsonPath("$.emailAddress", is("eric.roby@test.com")));
        //when

        //then
    }

    @Test
    public void studentInformationHttpRequestEmptyResponse() throws Exception{
        //given
        Optional<CollegeStudent> student = studentDao.findById(0);

        assertFalse(student.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/studentInformation/{id}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade wor not found")));
        //then
    }


    @Test
    public void createGradeValidHttpRequestEmptyResponseTest() throws Exception{
        //given

        this.mockMvc.perform(post("/api/grades")
                        .contentType(APPLICATION_JSON_UTF8)
                        .param("grade", "85.00")
                        .param("gradeType", "math")
                        .param("studentId", "0"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade wor not found")));
        //when

        //then
    }

    @Test
    public void createGradeValidHttpRequestTest() throws Exception{
        //given

        this.mockMvc.perform(post("/api/grades")
                        .contentType(APPLICATION_JSON_UTF8)
                        .param("grade", "85.00")
                        .param("gradeType", "math")
                        .param("studentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Eric")))
                .andExpect(jsonPath("$.lastName", is("Roby")))
                .andExpect(jsonPath("$.emailAddress", is("eric.roby@test.com")))
                .andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(2)));
        //when

        //then
    }


    @Test
    public void createInvalidGradeTypeHttpRequestTest() throws Exception{
        //given
        this.mockMvc.perform(post("/api/grades")
                        .contentType(APPLICATION_JSON_UTF8)
                        .param("grade", "85.00")
                        .param("gradeType", "literature")
                        .param("studentId", "1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade wor not found")));


        //when

        //then
    }

    @Test
    public void deleteGradeHttpRequestTest() throws Exception{
        //given

        Optional<MathGrade> mathGrade = mathGradeDao.findById(1);
        assertTrue(mathGrade.isPresent());
        //when

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/grades/{id}/{gradeType}",
                1, "math"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Eric")))
                .andExpect(jsonPath("$.lastName", is("Roby")))
                .andExpect(jsonPath("$.emailAddress", is("eric.roby@test.com")))
                .andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(0)));;

        //then
    }


    @Test
    public void deleteInvalidGradeHttpRequestTest() throws Exception{
        //given

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/grades/{id}/{gradeType}",
                        2, "history"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade wor not found")));

        //then
    }



    @Test
    public void deleteInvalidGradeTpeHttpRequestTest() throws Exception{
        //given

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/grades/{id}/{gradeType}",
                        1, "literature"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade wor not found")));

        //then
    }


    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }

}