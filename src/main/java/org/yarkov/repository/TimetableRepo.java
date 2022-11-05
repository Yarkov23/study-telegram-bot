package org.yarkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yarkov.entity.Timetable;

@Repository
public interface TimetableRepo extends JpaRepository<Timetable, Long> {
}
