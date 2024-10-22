package com.example.sec02;

import com.example.sec02.models.CollegeStudent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class Sec02Application {

    public static void main(String[] args) {
        SpringApplication.run(Sec02Application.class, args);
    }

    @Bean(name ="collegeStudent")
    @Scope(value = "prototype")
    CollegeStudent getCollegeStudent(){
        return new CollegeStudent();
    }

}
