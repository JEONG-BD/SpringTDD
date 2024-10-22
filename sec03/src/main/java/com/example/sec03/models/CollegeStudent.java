package com.example.sec03.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CollegeStudent implements Student{
    private String firstName;

    private String lastName;

    private String emailAddress;

    private StudentGrades studentGrades;


    public CollegeStudent() {
    }

    public CollegeStudent(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
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
