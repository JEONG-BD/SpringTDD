package com.example.sec04.controller;
import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.domain.GradeBook;
import com.example.sec04.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeBookController {

    @Autowired
    private GradeBook gradeBook;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public String getStudents(Model model){
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        model.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model model){
        return "studentInformation";
    }

    @PostMapping("/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model model){
        studentAndGradeService.createStudent(student.getFirstName(), student.getLastName(), student.getEmailAddress());
        return "index";
    }
}
