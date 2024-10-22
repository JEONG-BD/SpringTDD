package com.example.sec03;

import com.example.sec03.dao.ApplicationDao;
import com.example.sec03.models.CollegeStudent;
import com.example.sec03.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class Sec03Application {

    public static void main(String[] args) {
        SpringApplication.run(Sec03Application.class, args);
    }

    @Bean(name="applicationExample")
    ApplicationService getApplicationService(){
        return new ApplicationService();
    }


    @Bean(name="applicationDao")
    ApplicationDao getApplicationDao(){
        return new ApplicationDao();
    }


    @Bean(name="collegeStudent")
    @Scope(value="prototype")
    CollegeStudent getCollegeStudent(){
        return new CollegeStudent();
    }
}
