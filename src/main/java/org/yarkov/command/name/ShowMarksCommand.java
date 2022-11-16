package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.Role;
import org.yarkov.entity.Student;
import org.yarkov.entity.StudentTheme;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;

import java.util.List;
import java.util.Optional;

@Component
public class ShowMarksCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private StudentThemeService studentThemeService;

    private StudentService studentService;

    public ShowMarksCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

        User from = update.getMessage().getFrom();
        Optional<Student> foundedStud = studentService.findByTelegramId(from.getId().intValue());
        Student student = foundedStud.get();

        Role role = student.getRole();
        System.out.println(role);

        String chatId = update.getMessage().getChatId().toString();
        List<StudentTheme> studentThemes = studentThemeService.findAll();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Журнал оцінок: " + "\n");

        for (StudentTheme studentTheme : studentThemes) {
            String fullName = studentTheme.getStudentId().getFullName();
            Integer mark = studentTheme.getMark();

            stringBuilder.append(
                    "Студент: " + fullName + "\n"
                            + "Оцінка: " + mark + "\n\n"
            );
        }

        sendBotMessageService.sendMessage(chatId, stringBuilder.toString());

    }

    @Override
    public String getCommandName() {
        return CommandName.SHOW_MARKS.getCommandName();
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
