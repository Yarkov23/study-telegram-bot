package org.yarkov.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.entity.State;
import org.yarkov.entity.Student;
import org.yarkov.entity.Subject;
import org.yarkov.entity.Theme;
import org.yarkov.service.SendBotMessageServiceImpl;
import org.yarkov.service.StudentService;
import org.yarkov.service.SubjectService;
import org.yarkov.service.ThemeService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class AddThemeState implements StateProcess {

    private ThemeService themeService;

    private StudentService studentService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private SendBotMessageServiceImpl sendBotMessageService;

    private SubjectService subjectService;

    public State getState() {
        return State.ADD_THEME;
    }

    public void process(Update update, String step) {
        User from = update.getMessage().getFrom();
        Optional<Student> foundedStudent = studentService.findByTelegramId(from.getId().intValue());
        Student student = foundedStudent.get();

        String chatId = update.getMessage().getChatId().toString();

        switch (step) {
            case "Ввести назву теми" -> {
                Theme theme = new Theme();
                StringBuilder stringBuilder = new StringBuilder();
                String text = update.getMessage().getText();
                theme.setCaption(text);
                String convertedTheme = convertToDatabaseColumn(theme);
                student.setStateObject(convertedTheme);
                student.setStep("Ввести назву предмету");
                studentService.save(student);
                List<Subject> subjects = subjectService.findAll();

                for (Subject subject : subjects) {
                    stringBuilder.append(subject.getId() + ". " + subject.getCaption() + "\n");
                }

                sendBotMessageService.sendMessage(chatId,
                        "Введіть номер предмету який ви хочете обрати для цієї теми: " + "\n" +
                                stringBuilder);
            }
            case "Ввести назву предмету" -> {
                String stateObject = student.getStateObject();
                Theme theme = (Theme) convertToEntityAttribute(stateObject);
                String text = update.getMessage().getText();
                Subject subject = subjectService.findById(text);

                if (subject == null) {
                    sendBotMessageService.sendMessage(chatId,
                            "Некоректний ввід даних");
                    student.setState(State.DEFAULT);
                    student.setStep("DEFAULT");
                    student.setStateObject(null);
                    studentService.save(student);
                }

                theme.setSubjectId(subject);
                themeService.save(theme);
                student.setState(State.DEFAULT);
                student.setStep("DEFAULT");
                student.setStateObject(null);
                studentService.save(student);
            }
        }
    }

    public String convertToDatabaseColumn(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    public Object convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<Theme>() {
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
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }
}
