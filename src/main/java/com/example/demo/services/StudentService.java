package com.example.demo.services;

import com.example.demo.dbo.StudentRepository;
import com.example.demo.models.Student;
import com.example.demo.models.Todo;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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

    public List<Todo> getTodosGson () {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
                .build();

        HttpResponse<String> response = null;
        List<Todo> todo = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            todo = new Gson().fromJson(response.body(), ArrayList.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Todo[] todo = objectMapper.readValue(response, Todo[].class);
        //List<Todo> todo = gson.fromJson(response, new TypeToken<List<Todo>>(){}.getType());
        return todo;
    }


}
