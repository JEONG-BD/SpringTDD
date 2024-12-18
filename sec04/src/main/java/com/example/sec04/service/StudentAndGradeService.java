package com.example.sec04.service;

import com.example.sec04.domain.*;
import com.example.sec04.repository.HistoryGradesDao;
import com.example.sec04.repository.MathGradesDao;
import com.example.sec04.repository.ScienceGradesDao;
import com.example.sec04.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {


    @Autowired
    private StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;


    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;


    @Autowired
    private MathGradesDao mathGradesDao;


    @Autowired
    private ScienceGradesDao scienceGradesDao;


    @Autowired
    private HistoryGradesDao historyGradesDao;


    @Autowired
    private StudentGrades studentGrades;

    public void createStudent(String firstName, String lastName, String emailAddress){
        CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int studentId){
        Optional<CollegeStudent> student = studentDao.findById(studentId);

        if (student.isPresent()){
            return true;
        }
        return false;
    }

    public void deleteStudent(int id){
        if(checkIfStudentIsNull(id)){
            studentDao.deleteById(id);
            mathGradesDao.deleteByStudentId(id);
            scienceGradesDao.deleteByStudentId(id);
            historyGradesDao.deleteByStudentId(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook(){
        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        return collegeStudents;
    }

    public GradeBook getGradeBookV2(){
        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        Iterable<MathGrade> mathGrades = mathGradesDao.findAll();
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findAll();
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findAll();

        GradeBook gradeBook = new GradeBook();

        for (CollegeStudent collegeStudent : collegeStudents){
            List<Grade> mathGradesPerStudent = new ArrayList<>();
            List<Grade> scienceGradesPerStudent = new ArrayList<>();
            List<Grade> historyGradesPerStudent = new ArrayList<>();


            for (MathGrade grade : mathGrades) {
                if(grade.getStudentId() == collegeStudent.getId()){
                    mathGradesPerStudent.add(grade);
                }
            }

            for (HistoryGrade grade : historyGrades) {
                if(grade.getStudentId() == collegeStudent.getId()){
                    historyGradesPerStudent.add(grade);
                }
            }

            for (ScienceGrade grade : scienceGrades) {
                if(grade.getStudentId() == collegeStudent.getId()){
                    scienceGradesPerStudent.add(grade);
                }
            }
            studentGrades.setMathGradeResults(mathGradesPerStudent);
            studentGrades.setHistoryGradeResults(historyGradesPerStudent);
            studentGrades.setScienceGradeResults(scienceGradesPerStudent);

            GradeBookCollegesStudent gradeBookCollegesStudent = new GradeBookCollegesStudent(collegeStudent.getId(),
                    collegeStudent.getFirstName(),
                    collegeStudent.getLastName(),
                    collegeStudent.getEmailAddress(), studentGrades);

            gradeBook.getStudent().add(gradeBookCollegesStudent);
        }

        return gradeBook;


    }

    public boolean createGrade(double grade, int studentId, String gradeTye){

        if(!checkIfStudentIsNull(studentId)){
            return false;
        }

        if(grade >= 0 && grade <=100){

            if (gradeTye.equals("math")) {

                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);
                return true;
            }

            if (gradeTye.equals("science")) {
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradesDao.save(scienceGrade);
                return true;
            }

            if (gradeTye.equals("history")) {
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradesDao.save(historyGrade);
                return true;
            }

        }

        return false;

    }

    public int deleteGrade(int id, String gradeType){
        int studentId = 0 ;

        if(gradeType.equals("math")){
            Optional<MathGrade> grade = mathGradesDao.findById(id);

            if(!grade.isPresent()){
                return studentId;
            }
            studentId = grade.get().getStudentId();
            mathGradesDao.deleteById(id);
        }

        if(gradeType.equals("science")){
            Optional<ScienceGrade> grade = scienceGradesDao.findById(id);

            if(!grade.isPresent()){
                return studentId;
            }
            studentId = grade.get().getStudentId();
            scienceGradesDao.deleteById(id);
        }

        if(gradeType.equals("history")){
            Optional<HistoryGrade> grade = historyGradesDao.findById(id);

            if(!grade.isPresent()){
                return studentId;
            }
            studentId = grade.get().getStudentId();
            historyGradesDao.deleteById(id);
        }

        return studentId;
    }


    public GradeBookCollegesStudent studentInformation(int id) {

        if(!checkIfStudentIsNull(id)){
            return null ;
        }

        Optional<CollegeStudent> student = studentDao.findById(id);
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(id);

        List<Grade> mathGradeList =  new ArrayList<>();
        mathGrades.forEach(mathGradeList::add);
        List<Grade> historyGradeList =  new ArrayList<>();
        historyGrades.forEach(historyGradeList::add);
        List<Grade> scienceGradeList =  new ArrayList<>();
        scienceGrades.forEach(scienceGradeList::add);

        studentGrades.setMathGradeResults(mathGradeList);
        studentGrades.setHistoryGradeResults(historyGradeList);
        studentGrades.setScienceGradeResults(scienceGradeList);

        GradeBookCollegesStudent gradeBookCollegesStudent = new GradeBookCollegesStudent(student.get().getId(), student.get().getFirstName(), student.get().getLastName(), student.get().getEmailAddress(), studentGrades);

        return gradeBookCollegesStudent;
    }

    public void configureStudentInformation(int id, Model model){

        GradeBookCollegesStudent studentEntity =  studentInformation(id);
        System.out.println("studentEntity = " + studentEntity);
        System.out.println("studentEntity = " + studentEntity);
        System.out.println("studentEntity = " + studentEntity);

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


    }
}
