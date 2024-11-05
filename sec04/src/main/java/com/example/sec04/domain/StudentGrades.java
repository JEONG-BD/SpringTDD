package com.example.sec04.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@NoArgsConstructor
@Getter
@Setter
public class StudentGrades {


    private List<Grade> mathGradeResults;

    private List<Grade> scienceGradeResults;

    private List<Grade> historyGradeResults;

    public double addGradeResultsForSingle(List<Grade> grades){
        double result = 0;

        for (Grade grade : grades) {
            result += grade.getGrade();
        }
        return result;
    }

    public double findGradePointAverage(List<Grade> grades){
        int lengthOfGrades = grades.size();
        double sum = addGradeResultsForSingle(grades);
        double result = sum / lengthOfGrades;

        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();
    }

    @Override
    public String toString() {
        return "StudentGrades{" +
                "mathGradeResults=" + mathGradeResults +
                '}';
    }
}
