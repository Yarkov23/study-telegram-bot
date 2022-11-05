package org.yarkov.service;

import org.springframework.stereotype.Service;
import org.yarkov.entity.Timetable;
import org.yarkov.repository.TimetableRepo;

import java.util.List;

@Service
public class TimetableService {

    private TimetableRepo timetableRepo;

    public TimetableService(TimetableRepo timetableRepo) {
        this.timetableRepo = timetableRepo;
    }

    public List<Timetable> findAll() {
        return timetableRepo.findAll();
    }

}
