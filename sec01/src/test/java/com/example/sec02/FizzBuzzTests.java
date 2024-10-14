package com.example.sec02;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTests {

    //If number is divisible by 3, print Fizz
    //If number is divisible by 5, print Buzz
    //If number is divisible 3 and 5, print FizzBuzz
    //If number is Not divisible by 3 or 5, then pring the number

    @DisplayName("Divisible by Three")
    @Test
    @Order(1)
    public void testForDivisibleByThree() throws Exception{
        //given
        String expected = "Fizz";
        //fail("fail");
        //when

        //then
        assertEquals(expected, FizzBuzz.computer(3), "Should return Fizz");

    }


    @DisplayName("Divisible by Five")
    @Test
    @Order(2)
    public void testForDivisibleByFive() throws Exception{
        //given
        String expected = "Buzz";
        //fail("fail");
        //when

        //then
        assertEquals(expected, FizzBuzz.computer(5), "Should return Buzz");

    }

    @DisplayName("Divisible by Three and Five")
    @Test
    @Order(3)
    public void testForDivisibleByThreeAndFive() throws Exception{
        //given
        String expected = "FizzBuzz";
        //fail("fail");
        //when

        //then
        assertEquals(expected, FizzBuzz.computer(15), "Should return FizzBuzz");

    }

    @DisplayName("Not Divisible by Three or Five")
    @Test
    @Order(4)
    public void testForNotDivisibleByThreeOrFive() throws Exception{
        //given
        String expected = "1";
        //fail("fail");
        //when

        //then
        assertEquals(expected, FizzBuzz.computer(1), "Should return 1");

    }



}
