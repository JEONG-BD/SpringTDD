package com.example.sec02.models;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class StudentGrades {


    List<Double> matchGradeResults;

    public StudentGrades() {
    }

    public StudentGrades(List<Double> matchGradeResults) {
        this.matchGradeResults = matchGradeResults;
    }

    public double addGradeResultsForSingleClass(List<Double> grades){
        double result = 0;
        for (Double grade : grades) {
            result += grade;
        }
        return result;

    }

    public double findGradePointAverage(List<Double> grades){
        int lengthOfGrades = grades.size();
        double sum = addGradeResultsForSingleClass(grades);
        double result = sum/lengthOfGrades;


        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();
    }

    public Boolean isGradeGreater(double gradeOne, double gradeTwo){
        if (gradeOne > gradeTwo) {
            return true;
        }
        return false;
    }
}
