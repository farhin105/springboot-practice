package com.example.demo.controller;

import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudentList () {
        return studentService.getStudentList();
    }

    @PostMapping
    public void addStudent (@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping (path = "{studentId}")
    public void deleteStudent ( @PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent (@PathVariable("studentId") Long studentId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }
}
