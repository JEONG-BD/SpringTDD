package com.example.sec04.controller;

import com.example.sec04.domain.CollegeStudent;
import com.example.sec04.domain.GradeBook;
import com.example.sec04.domain.GradeBookCollegesStudent;
import com.example.sec04.exceptionhandling.StudentOrGradeErrorResponse;
import com.example.sec04.exceptionhandling.StudentOrGradeNotFoundException;
import com.example.sec04.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestGradeBookController {


    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private GradeBook gradeBook;

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public List<GradeBookCollegesStudent> getStudents() {
        //GradeBook gradeBook = studentService.getGradeBookV2();
        gradeBook = studentService.getGradeBookV2();

        return gradeBook.getStudent();
    }

    @GetMapping("/api/studentInformation/{id}")
    public GradeBookCollegesStudent studentInformation(@PathVariable int id) {

        if (!studentService.checkIfStudentIsNull(id)) {
            throw new StudentOrGradeNotFoundException("Student or Grade wor not found");
        }

        GradeBookCollegesStudent gradeBookCollegesStudent = studentService.studentInformation(id);

        return gradeBookCollegesStudent;
    }

    @PostMapping("/api")
    public List<GradeBookCollegesStudent> createStudent(@RequestBody CollegeStudent student) {
        studentService.createStudent(student.getFirstName(), student.getLastName(), student.getEmailAddress());

        gradeBook = studentService.getGradeBookV2();

        return gradeBook.getStudent();
    }

    @DeleteMapping("/api/student/{id}")
    public List<GradeBookCollegesStudent> deleteStudent(@PathVariable int id) {

        if (!studentService.checkIfStudentIsNull(id)) {
            throw new StudentOrGradeNotFoundException("Student or Grade wor not found");
        }

        studentService.deleteStudent(id);

        gradeBook = studentService.getGradeBookV2();

        return gradeBook.getStudent();
    }


    @PostMapping("/api/grades")
    public GradeBookCollegesStudent createGrade(@RequestParam("grade") double grade,
                                                @RequestParam("gradeType") String gradeType,
                                                @RequestParam("studentId") int studentId) {

        if (!studentService.checkIfStudentIsNull(studentId)) {
            throw new StudentOrGradeNotFoundException("Student or Grade wor not found");
        }

        boolean success = studentService.createGrade(grade, studentId, gradeType);

        if (!success) {
            throw new StudentOrGradeNotFoundException("Student or Grade wor not found");
        }

        GradeBookCollegesStudent student = studentService.studentInformation(studentId);

        if (student == null) {
            throw new StudentOrGradeNotFoundException("Student or Grade wor not found");
        }

        return student;
    }
    @DeleteMapping("/api/grades/{id}/{gradeType}")
    public GradeBookCollegesStudent deleteGrades(@PathVariable int id,
                               @PathVariable String gradeType){

        int studentId = studentService.deleteGrade(id, gradeType);

        if(studentId ==0 ){
            throw new StudentOrGradeNotFoundException("Student or Grade wor not found");
        }
        GradeBookCollegesStudent student = studentService.studentInformation(studentId);

        return student;
    }

    @ExceptionHandler
    public ResponseEntity<StudentOrGradeErrorResponse> handleException(StudentOrGradeErrorResponse exc){
        StudentOrGradeErrorResponse errorResponse = new StudentOrGradeErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentOrGradeErrorResponse> handleException(Exception exc) {
        StudentOrGradeErrorResponse errorResponse = new StudentOrGradeErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());


        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }


}