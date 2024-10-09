package com.example.sec01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


public class DemoUtilsTests {

    @Test
    public void testEqualsAndNotEquals() throws Exception{
        DemoUtils demoUtils = new DemoUtils();
        assertEquals(6, demoUtils.add(2, 4), "2 + 5 must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1+9 must not be 6");
    }
}
