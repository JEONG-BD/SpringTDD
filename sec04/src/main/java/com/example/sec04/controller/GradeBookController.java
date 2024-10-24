package com.example.sec04.controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GradeBookController {

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public String getStudents(Model model){
        return "index";
    }
}
