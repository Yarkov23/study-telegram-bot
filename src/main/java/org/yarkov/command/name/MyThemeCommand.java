package org.yarkov.command.name;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.Student;
import org.yarkov.entity.StudentTheme;
import org.yarkov.entity.Theme;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;

import java.util.Optional;

@Component
public class MyThemeCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private StudentThemeService studentThemeService;

    private StudentService studentService;

    public MyThemeCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        User from = update.getMessage().getFrom();
        StringBuilder stringBuilder = new StringBuilder();
        Optional<Student> foundedStud = studentService.findByTelegramId(from.getId().intValue());
        Student currentStud = foundedStud.get();

        if (currentStud.getRole().getRoleName().equals("Teacher")) {
            sendBotMessageService.sendMessage(chatId,
                    "Ця команда не доступна для вас.");
            return;
        }

        if (foundedStud.isPresent()) {
            Student student = foundedStud.get();

            StudentTheme studentTheme = studentThemeService.findByStudentId(student);
            Theme result = studentTheme.getThemeId();

            String caption = result.getCaption();

            stringBuilder.append("<b>Ваша тема:</b> " + "\n"
                    + caption + "\n");

            sendBotMessageService.sendMessage(chatId, stringBuilder.toString());

        }else {
            sendBotMessageService.sendMessage(chatId, "Дані відсутні.");
        }

    }

    public String getCommandName() {
        return CommandName.MY_THEME.getCommandName();
    }

    @Autowired
    public void setStudentThemeService(StudentThemeService studentThemeService) {
        this.studentThemeService = studentThemeService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
