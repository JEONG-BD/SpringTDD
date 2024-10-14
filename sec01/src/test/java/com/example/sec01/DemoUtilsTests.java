package com.example.sec01;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
//@TestMethodOrder(MethodOrderer.MethodName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    //@DisplayName("Equals and Not Equals")
    public void testEqualsAndNotEquals() throws Exception{
        //DemoUtils demoUtils = new DemoUtils();
        assertEquals(6, demoUtils.add(2, 4), "2 + 5 must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1+9 must not be 6");
        System.out.println("Running testEqualsAndNotEquals");
    }

    @Test
    @Order(2)
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
    @Order(3)
    //@DisplayName("Same and Not Same")
    public void testSameAndNotSame() throws Exception{
        //given
         String str = "tdd";
         assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Object should refer to same object");
         assertNotSame(str, demoUtils.getAcademy(), "Object should not refer to same object");
         //when

        //then
    }

    @Test
    @Order(4)
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
    @Order(5)
    public void testArrayEquals() throws Exception{
        //given
        String [] testArray = {"A", "B", "C"};
        assertArrayEquals(testArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Array should be the same");
        //when

        //then
    }

    @Test
    @Order(6)
    public void testIterableEquals() throws Exception{
        //given
        List<String> testList = List.of("tdd", "spring", "code");
        //when
        assertIterableEquals(testList, demoUtils.getAcademyInList(), "Expected list should be same as actual list");
        //then
    }

    @Test
    @Order(7)
    public void testLineMatch() throws Exception{
        List<String> testList = List.of("tdd", "spring", "code");
        assertLinesMatch(testList, demoUtils.getAcademyInList(), "Line should match");
    }

    @Test
    @Order(8)
    public void testThrowAndDoesNotThrow() throws Exception{
        //given
        assertThrows(Exception.class, () -> {demoUtils.throwException(-1);}, "Should throw exception");
        //when
        assertDoesNotThrow(() -> {demoUtils.throwException(9);},"Should not throw exception");
        //then
    }

    @Test
    @Order(10)
    public void testTimeout() throws Exception{
        //given
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {demoUtils.checkTimeout();},
                "Method should execute 3 seconds");
    }
}
