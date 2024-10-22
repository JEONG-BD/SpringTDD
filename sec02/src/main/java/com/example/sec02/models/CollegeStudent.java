package com.example.sec02.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class CollegeStudent implements Student{

    private String firstName;
    private String lastName;
    private String emailAddress;
    private StudentGrades studentGrades;

    public CollegeStudent() {
    }

    @Override
    public String studentInformation() {
        return getFullName() + " " + getEmailAddress();
    }

    @Override
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
