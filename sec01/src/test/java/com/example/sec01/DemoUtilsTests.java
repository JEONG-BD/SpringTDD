package com.example.sec01;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

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
}
