package org.yarkov.service;

import org.springframework.stereotype.Service;
import org.yarkov.entity.Subject;
import org.yarkov.repository.SubjectRepo;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private SubjectRepo subjectRepo;

    public SubjectService(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    public List<Subject> findAll() {
        return subjectRepo.findAll();
    }

    public Subject findById(String id) {
        try {
            Long i = Long.parseLong(id);
            Optional<Subject> resultSubject = subjectRepo.findById(i);
            return resultSubject.orElse(null);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
