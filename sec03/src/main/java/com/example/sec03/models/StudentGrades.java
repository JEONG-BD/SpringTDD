package com.example.sec03.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@Getter
@Setter
@ToString
public class StudentGrades {

    List<Double> mathGradeResults;

    public StudentGrades() {
    }

    public StudentGrades(List<Double> methGradeResults) {
        this.mathGradeResults = mathGradeResults;
    }

    public double addGradeResultsForSingleClass(List<Double> grades) {
        double result = 0;
        for (Double grade : grades) {
            result += grade;
        }
        return result;
    }

    public double findGradePointAverage(List<Double> grades){
        int lengthOfGrades = grades.size();

        double sum = addGradeResultsForSingleClass(grades);
        double result = sum / lengthOfGrades;

        BigDecimal resultRound = BigDecimal.valueOf(result);

        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();
    }

    public Boolean isGradeGreater(double gradeOne, double gradeTwo){
        if (gradeOne> gradeTwo){
            return true;
        }
        return false;
    }

    public  Object checkNull(Object obj){
        if (obj != null){
            return obj;
        }
        return null;
    }


}
