package com.example.sec04.controller;
import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.domain.GradeBook;
import com.example.sec04.domain.GradeBookCollegesStudent;
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

        if(!studentAndGradeService.checkIfStudentIsNull(id)){
            return "error";
        }

        /*GradeBookCollegesStudent studentEntity = studentAndGradeService.studentInformation(id);

        model.addAttribute("student", studentEntity);

        if (studentEntity.getStudentGrades().getMathGradeResults().size() > 0) {
            model.addAttribute("mathAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getMathGradeResults()
            ));
        } else {
            model.addAttribute("mathAverage", "N/A");
        }

        if (studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
            model.addAttribute("scienceAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getScienceGradeResults()
            ));
        } else {
            model.addAttribute("scienceAverage", "N/A");
        }

        if (studentEntity.getStudentGrades().getHistoryGradeResults().size() > 0) {
            model.addAttribute("historyAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getHistoryGradeResults()
            ));
        } else {
            model.addAttribute("historyAverage", "N/A");
        }*/

        studentAndGradeService.configureStudentInformation(id, model);
        return "studentInformation";
    }

    @PostMapping("/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model model){

        System.out.println("student = " + student);
        System.out.println("student = " + student);

        studentAndGradeService.createStudent(student.getFirstName(), student.getLastName(), student.getEmailAddress());
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        model.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id, Model model){

        if(!studentAndGradeService.checkIfStudentIsNull(id)){
            return "error";
        }

        studentAndGradeService.deleteStudent(id);
        Iterable<CollegeStudent> students = studentAndGradeService.getGradeBook();
        model.addAttribute("students", students);
        return "index";
    }

    @PostMapping("/grades")
    public String createGrade(@RequestParam("grade") double grade,
                              @RequestParam("gradeType") String gradeType,
                              @RequestParam("studentId") int studentId,
                              Model model){


        if(!studentAndGradeService.checkIfStudentIsNull(studentId)){
            System.out.println("NullNull");
            System.out.println("NullNull");

            return "error";
        }

        boolean success = studentAndGradeService.createGrade(grade, studentId, gradeType);

        if(!success){
            return "error";
        }
        /*
        GradeBookCollegesStudent studentEntity =  studentAndGradeService.studentInformation(studentId);
        model.addAttribute("student", studentEntity);

        if (studentEntity.getStudentGrades().getMathGradeResults().size() > 0) {
            model.addAttribute("mathAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getMathGradeResults()
            ));
        } else {
            model.addAttribute("mathAverage", "N/A");
        }

        if (studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
            model.addAttribute("scienceAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getScienceGradeResults()
            ));
        } else {
            model.addAttribute("scienceAverage", "N/A");
        }

        if (studentEntity.getStudentGrades().getHistoryGradeResults().size() > 0) {
            model.addAttribute("historyAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getHistoryGradeResults()
            ));
        } else {
            model.addAttribute("historyAverage", "N/A");
        }
        */
        studentAndGradeService.configureStudentInformation(studentId, model);
        return "studentInformation";
    }


}
