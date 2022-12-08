package org.yarkov.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.dto.StudentDTO;
import org.yarkov.entity.State;
import org.yarkov.entity.Student;
import org.yarkov.entity.StudentTheme;
import org.yarkov.entity.Theme;
import org.yarkov.service.SendBotMessageServiceImpl;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;
import org.yarkov.service.ThemeService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SetThemeState implements StateProcess {

    private SendBotMessageServiceImpl sendBotMessageService;

    private StudentThemeService studentThemeService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private StudentService studentService;

    private ThemeService themeService;

    public State getState() {
        return State.SET_THEME;
    }

    public void process(Update update, String step) {
        User from = update.getMessage().getFrom();
        Optional<Student> foundedStudent = studentService.findByTelegramId(from.getId().intValue());
        Student student = foundedStudent.get();

        String chatId = update.getMessage().getChatId().toString();

        switch (step) {
            case "Вибрати студента" -> {
                String text = update.getMessage().getText();
                StringBuilder stringBuilder = new StringBuilder();
                Optional<Student> foundedStud = studentService.findByFullName(text);

                if (foundedStud.isPresent()) {
                    Student resultStudent = foundedStud.get();

                    StudentDTO studentDTO = new StudentDTO();

                    studentDTO.setFullName(resultStudent.getFullName());
                    studentDTO.setGroup(resultStudent.getGroup());

                    String converted = convertToDatabaseColumn(studentDTO);

                    student.setStep("Вибрати тему");
                    student.setStateObject(converted);
                    studentService.save(student);

                    List<Theme> themes = themeService.findAll();

                    for (Theme theme : themes) {
                        stringBuilder.append(theme.getId() + " - " + theme.getCaption() + "\n");
                    }

                    sendBotMessageService.sendMessage(chatId,
                            "Введіть номер теми із даного списку: " + "\n" + stringBuilder);

                } else {
                    sendBotMessageService.sendMessage(chatId,
                            "Студента не існує або він був введенний не вірно.");
                    exit(student);
                }
            }
            case "Вибрати тему" -> {
                String text = update.getMessage().getText();

                Optional<Theme> foundedTheme = themeService.findById(text);

                if (foundedTheme.isPresent()) {
                    StudentDTO studentDTO = (StudentDTO) convertToStudentAttribute(student.getStateObject());
                    Optional<Student> stud = studentService.findByFullName(studentDTO.getFullName());
                    Student currentStud = stud.get();
                    StudentTheme studentTheme = studentThemeService.findByStudentId(currentStud);
                    studentTheme.setThemeId(foundedTheme.get());
                    studentThemeService.save(studentTheme);
                    exit(student);
                } else {
                    sendBotMessageService.sendMessage(chatId,
                            "Теми не існує або дані введенно не вірно.");
                    student.setStep("DEFAULT");
                    exit(student);
                }

            }

        }
    }

    public void exit(Student student) {
        student.setStep("DEFAULT");
        student.setState(State.DEFAULT);
        student.setStateObject(null);
        studentService.save(student);
    }

    public String convertToDatabaseColumn(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    public Object convertToStudentAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<StudentDTO>() {
            });
        } catch (IOException ex) {
            return null;
        }
    }

    @Autowired
    public void setSendBotMessageService(SendBotMessageServiceImpl sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setStudentThemeService(StudentThemeService studentThemeService) {
        this.studentThemeService = studentThemeService;
    }

    @Autowired
    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }
}
