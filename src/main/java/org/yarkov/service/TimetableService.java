package org.yarkov.service;

import org.springframework.stereotype.Service;
import org.yarkov.entity.StudentTheme;
import org.yarkov.entity.Timetable;
import org.yarkov.repository.TimetableRepo;

import java.util.List;
import java.util.Optional;

@Service
public class TimetableService {

    private TimetableRepo timetableRepo;

    public TimetableService(TimetableRepo timetableRepo) {
        this.timetableRepo = timetableRepo;
    }

    public List<Timetable> findAll() {
        return timetableRepo.findAll();
    }

    public Optional<Timetable> findByStudentThemeId(StudentTheme studentTheme) {
        return timetableRepo.findTimetablesByStudentTheme(studentTheme);
    }

    public void save(Timetable timetable) {
        if (timetable == null)
            return;
        timetableRepo.save(timetable);
    }
}
