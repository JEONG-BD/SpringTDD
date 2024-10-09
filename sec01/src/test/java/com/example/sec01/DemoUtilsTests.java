package com.example.sec01;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)

public class DemoUtilsTests {

    /*DemoUtils demoUtils ;*/
    private final DemoUtils demoUtils = DemoUtils.getInstance();

    @BeforeEach
    void setUpBeforeEach(){
        System.out.println("@Before Each executes befores the execution of each test method");
    }

    @AfterEach
    void tearDownAfterEach(){
        System.out.println("Running @AfterEach");
    }

    @BeforeAll
    static void setUpBeforeEachClass(){
        System.out.println("@BeforeAll");
    }


    @AfterAll
    static void tearDownAfterAll(){
        System.out.println("@AfterAll");
    }

    @Test
    //@DisplayName("Equals and Not Equals")
    public void testEqualsAndNotEquals() throws Exception{
        //DemoUtils demoUtils = new DemoUtils();
        assertEquals(6, demoUtils.add(2, 4), "2 + 5 must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1+9 must not be 6");
        System.out.println("Running testEqualsAndNotEquals");
    }

    @Test
    //@DisplayName("Null and Not Null")
    public void testNullAndNotNull() throws Exception{
        //DemoUtils demoUtils = new DemoUtils();
        String str1 = null;
        String str2 = "tdd";

        assertNull(demoUtils.checkNull(str1), "Object should be Null");
        assertNotNull(demoUtils.checkNull(str2), "Object should be Null");
        System.out.println("Running testNullAndNotNull");

    }


    @Test
    @DisplayName("Same and Not Same")
    public void testSameAndNotSame() throws Exception{
        //given
         String str = "tdd";
         assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Object should refer to same object");
         assertNotSame(str, demoUtils.getAcademy(), "Object should not refer to same object");
         //when

        //then
    }

    @Test
    public void testTrueAndFalse() throws Exception{
        //given
         int gradeOne = 30;
         int gradeTwo = 5;
        //when
        assertTrue(demoUtils.isGreater(gradeOne, gradeTwo), "This should return true");
        assertFalse(demoUtils.isGreater(gradeTwo, gradeOne), "This should return false");
        //then
    }

    @Test
    public void testArrayEquals() throws Exception{
        //given
        String [] testArray = {"A", "B", "C"};
        assertArrayEquals(testArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Array should be the same");
        //when

        //then
    }

    @Test
    public void testIterableEquals() throws Exception{
        //given
        List<String> testList = List.of("tdd", "spring", "code");
        //when
        assertIterableEquals(testList, demoUtils.getAcademyInList(), "Expected list should be same as actual list");
        //then
    }

    @Test
    public void testLineMatch() throws Exception{
        List<String> testList = List.of("tdd", "spring", "code");
        assertLinesMatch(testList, demoUtils.getAcademyInList(), "Line should match");

    }
}
