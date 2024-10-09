package com.example.sec01;

import lombok.Getter;

import java.util.List;

@Getter
public class DemoUtils {

    private static DemoUtils instance = new DemoUtils();
    private String academy = "tdd Academy";
    private String academyDuplicate = academy;
    private String[] firstThreeLettersOfAlphabet = {"A", "B", "C"};
    private List<String> academyInList = List.of("tdd", "spring", "code");
    private DemoUtils() {

    }

    public static DemoUtils getInstance(){
        return instance;
    }

    public int add(int a, int b) {
        return a + b;
    }

    public Object checkNull(Object obj){
        if (obj != null){
            return obj;
        }
        return null;
    }

    public Boolean isGreater(int n1, int n2){
        if (n1 > n2){
            return true;
        }
        return false;
    }



}
