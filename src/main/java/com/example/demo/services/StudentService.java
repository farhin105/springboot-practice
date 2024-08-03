package com.example.demo.services;

import com.example.demo.dbo.StudentRepository;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudentList () {
        return studentRepository.findAll();
    }

    public void addStudent (Student student) {
        Optional<Student> studentByEmail = studentRepository
                .findStudentByEmail(student.getEmail());

        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent (Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        }
        else {
            throw new IllegalStateException("student with id = "+studentId+" does not exist");
        }
    }

    @Transactional
    public void updateStudent (Long studentId, String name, String email) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalStateException("student with id = "+studentId+" does not exist");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id = "+studentId+" does not exist"));

        if (name!=null &&
                name.length()>0 &&
                !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email!=null &&
                email.length()>0 &&
                !Objects.equals(student.getEmail(), email)) {

            if (studentRepository.findStudentByEmail(email).isPresent()) {
                throw new IllegalStateException("student with email = "+email+" already exists");
            }
            student.setEmail(email);
        }
    }


}
