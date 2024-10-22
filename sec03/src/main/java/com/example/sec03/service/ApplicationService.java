package com.example.sec03.service;

import com.example.sec03.dao.ApplicationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationDao applicationDao;

    public double addGradeResultsForSingleClass(List<Double> grades) {
        return applicationDao.addGradeResultsForSingleClass(grades);
    }

    public double findGradePointAverage(List<Double> grades){
        return applicationDao.findGradePointAverage(grades);
    }


    public  Object checkNull(Object obj){
        return applicationDao.checkNull(obj);
    }

}
