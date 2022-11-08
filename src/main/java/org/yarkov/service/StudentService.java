package org.yarkov.service;

import org.springframework.stereotype.Service;
import org.yarkov.entity.Student;
import org.yarkov.repository.StudentRepo;

import java.util.Optional;

@Service
public class StudentService {

    private StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Optional<Student> findByFullName(String fullName) {
        return Optional.ofNullable(studentRepo.findStudentByFullName(fullName));
    }

}
