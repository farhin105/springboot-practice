package com.example.demo.controller;

import com.example.demo.models.Student;
import com.example.demo.models.Todo;
import com.example.demo.services.YouTubeService;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final YouTubeService youtubeService;

    @Autowired
    public StudentController(StudentService studentService, YouTubeService youtubeService) {
        this.studentService = studentService;
        this.youtubeService = youtubeService;
    }

    @GetMapping
    public List<Student> getStudentList () {
        return studentService.getStudentList();
    }

    @GetMapping(path = "/todo")
    public List<Todo> getTodo () {
        return studentService.getTodosGson();
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
