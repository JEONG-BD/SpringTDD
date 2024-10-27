package com.example.sec04.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class CollegeStudent implements Student{

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
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String getFullName() {
        return getFullName() + " " + getEmailAddress();
    }
}