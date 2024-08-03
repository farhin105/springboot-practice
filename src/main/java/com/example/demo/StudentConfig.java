package com.example.demo;

import com.example.demo.dbo.StudentRepository;
import com.example.demo.models.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner (
            StudentRepository studentRepository) {
        return args -> {
            studentRepository.save(new Student("Seeum",
                    LocalDate.of(1994, Month.AUGUST, 3),
                    "seeum@gmail.com"));

            studentRepository.save(new Student("Samia",
                    LocalDate.of(1994, Month.SEPTEMBER, 30),
                    "samia@gmail.com"));
        };
    }
}
