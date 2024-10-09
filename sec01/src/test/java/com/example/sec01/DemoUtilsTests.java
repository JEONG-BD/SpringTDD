package com.example.sec01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


public class DemoUtilsTests {

    private final DemoUtils demoUtils = DemoUtils.getInstance();
    @Test
    public void testEqualsAndNotEquals() throws Exception{
        //DemoUtils demoUtils = new DemoUtils();
        assertEquals(6, demoUtils.add(2, 4), "2 + 5 must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1+9 must not be 6");
    }

    @Test
    public void testNullAndNotNull() throws Exception{
        //DemoUtils demoUtils = new DemoUtils();
        String str1 = null;
        String str2 = "tdd";

        assertNull(demoUtils.checkNull(str1), "Object should be Null");
        assertNotNull(demoUtils.checkNull(str2), "Object should be Null");
    }
}
