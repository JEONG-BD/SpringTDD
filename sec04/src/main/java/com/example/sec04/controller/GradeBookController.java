package com.example.sec04.controller;
import com.example.sec04.domain.GradeBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GradeBookController {

    @Autowired
    private GradeBook gradeBook;

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public String getStudents(Model model){
        return "index";
    }
}
