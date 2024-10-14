package com.example.sec02;

public class FizzBuzz {

    //If number is divisible by 3, print Fizz
    //If number is divisible by 5, print Buzz
    //If number is divisible 3 and 5, print FizzBuzz
    //If number is Not divisible by 3 or 5, then pring the number

    public static String computer(int i) {

        if ((i % 3 == 0) && (i % 5 == 0)) {
            return "FizzBuzz";
        } else if (i % 3 == 0) {
            return "Fizz";
        } else if (i % 5 == 0) {
            return "Buzz";
        } else {
            return Integer.toString(i);
        }
    }
}
