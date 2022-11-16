package org.yarkov.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.entity.State;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;

@Component
public class SetMarkState implements StateProcess {

    private StudentService studentService;

    private StudentThemeService studentThemeService;

    public State getState() {
        return State.SET_MARK;
    }


    public void process(Update update, String step) {
        switch (step) {
            case "Ввести студента" -> {

            }
        }
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setStudentThemeService(StudentThemeService studentThemeService) {
        this.studentThemeService = studentThemeService;
    }
}
