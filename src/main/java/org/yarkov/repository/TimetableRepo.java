package org.yarkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yarkov.entity.StudentTheme;
import org.yarkov.entity.Timetable;

import java.util.Optional;

@Repository
public interface TimetableRepo extends JpaRepository<Timetable, Long> {

    Optional<Timetable> findTimetablesByStudentTheme(StudentTheme studentTheme);

}
