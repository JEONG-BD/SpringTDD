package com.example.sec02.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class ColleageStudent implements Student{

    private String firstName;
    private String lastName;
    private String emailAddress;
    private StudentGrades studentGrades;

    public ColleageStudent() {
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
