package com.example.sec04.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Setter
@Getter
public class GradeBook {

    private List<GradeBookCollegesStudent> student = new ArrayList<>();

    public GradeBook() {

    }

    public GradeBook(List<GradeBookCollegesStudent> student) {
        this.student = student;
    }
}
