package org.yarkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yarkov.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    Student findStudentByFullName(String fullName);

}
