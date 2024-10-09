package com.example.sec01;

public class DemoUtils {

    private static DemoUtils instance = new DemoUtils();

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
}
