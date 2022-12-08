package org.yarkov.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.dto.StudentDTO;
import org.yarkov.entity.State;
import org.yarkov.entity.Student;
import org.yarkov.entity.StudentTheme;
import org.yarkov.entity.Timetable;
import org.yarkov.json.StudentJsonConverter;
import org.yarkov.service.SendBotMessageServiceImpl;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;
import org.yarkov.service.TimetableService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;

@Component
public class SetSubmissionState implements StateProcess {

    private final SendBotMessageServiceImpl sendBotMessageService;

    private TimetableService timetableService;

    private StudentService studentService;

    private StudentJsonConverter studentJsonConverter;

    private StudentThemeService studentThemeService;

    public SetSubmissionState(SendBotMessageServiceImpl sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public State getState() {
        return State.SET_SUBMISSION;
    }

    public void process(Update update, String step) {
        User from = update.getMessage().getFrom();
        Optional<Student> foundedStudent = studentService.findByTelegramId(from.getId().intValue());
        Student student = foundedStudent.get();

        String chatId = update.getMessage().getChatId().toString();

        switch (step) {
            case "Вибрати студента" -> {
                String text = update.getMessage().getText();
                Optional<Student> foundedStud = studentService.findByFullName(text);

                if (foundedStud.isPresent()) {
                    Student resultStudent = foundedStud.get();

                    StudentDTO studentDTO = new StudentDTO();

                    studentDTO.setFullName(resultStudent.getFullName());
                    studentDTO.setGroup(resultStudent.getGroup());

                    String converted = studentJsonConverter.convertToDatabaseColumn(studentDTO);

                    student.setStep("Ввести дату");
                    student.setStateObject(converted);
                    studentService.save(student);

                    sendBotMessageService.sendMessage(chatId,
                            "Введіть дату здачі роботи (MM/dd/yyyy): ");

                } else {
                    sendBotMessageService.sendMessage(chatId,
                            "Студента не існує або він був введенний не вірно.");
                    exit(student);
                }
            }
            case "Ввести дату" -> {
                String date = update.getMessage().getText();

                StudentDTO studentDTO = studentJsonConverter.convertToEntityAttribute(student.getStateObject());
                Optional<Student> studByFullName = studentService.findByFullName(studentDTO.getFullName());
                Student currentStud = studByFullName.get();
                StudentTheme studentTheme = studentThemeService.findByStudentId(currentStud);

                Optional<Timetable> timetableByStudentTheme = timetableService.findByStudentThemeId(studentTheme);

                Timetable timetable = timetableByStudentTheme.get();

                LocalDate localDate = null;

                try {
                    localDate = dateInput(date);
                } catch (DateTimeParseException exception) {
                    sendBotMessageService.sendMessage(chatId,
                            "Некоректний ввід.");
                    exit(student);
                }

                timetable.setDate(localDate);
                timetableService.save(timetable);
                sendBotMessageService.sendMessage(chatId,
                        "Назначено.");
                exit(student);
            }
        }
    }

    public LocalDate dateInput(String input) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(input, dateFormat);
    }

    public void exit(Student student) {
        student.setStep("DEFAULT");
        student.setState(State.DEFAULT);
        student.setStateObject(null);
        studentService.save(student);
    }

    @Autowired
    public void setTimetableService(TimetableService timetableService) {
        this.timetableService = timetableService;
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
    public void setStudentJsonConverter(StudentJsonConverter studentJsonConverter) {
        this.studentJsonConverter = studentJsonConverter;
    }
}
