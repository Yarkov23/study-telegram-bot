package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.Student;
import org.yarkov.entity.StudentTheme;
import org.yarkov.service.SendBotMessageServiceImpl;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;

import java.util.Optional;


@Component
public class MyMarkCommand implements Command {

    private final SendBotMessageServiceImpl sendBotMessageService;

    private StudentThemeService studentThemeService;

    private StudentService studentService;

    public MyMarkCommand(SendBotMessageServiceImpl sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public void execute(Update update) {
        User from = update.getMessage().getFrom();
        Optional<Student> foundedStud = studentService.findByTelegramId(from.getId().intValue());
        Student currentStud = foundedStud.get();

        String chatId = update.getMessage().getChatId().toString();
        StringBuilder stringBuilder = new StringBuilder();


        if (currentStud.getRole().getRoleName().equals("Teacher")) {
            sendBotMessageService.sendMessage(chatId,
                    "Ця команда не доступна для вас.");
            return;
        }

        if (foundedStud.isPresent()) {
            Student student = foundedStud.get();

            StudentTheme studentTheme = studentThemeService.findByStudentId(student);

            Integer mark = studentTheme.getMark();

            stringBuilder.append("Ваша оцінка: " + mark + "\n");

            sendBotMessageService.sendMessage(chatId, stringBuilder.toString());

        } else {
            sendBotMessageService.sendMessage(chatId, "Дані відсутні.");
        }

    }


    public String getCommandName() {
        return CommandName.MY_MARK.getCommandName();
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
