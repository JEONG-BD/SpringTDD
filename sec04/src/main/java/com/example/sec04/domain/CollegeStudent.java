package com.example.sec04.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Setter
@Getter
@ToString
public class CollegeStudent implements Student{
    public CollegeStudent() {
    }

    public CollegeStudent(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column String emailAddress;


    @Override
    public String studentInformation() {
        return getFullName() + " " + getEmailAddress();
    }

    @Override
    public String getFullName() {
        return getFirstName()+ " " + getLastName();
    }
}
