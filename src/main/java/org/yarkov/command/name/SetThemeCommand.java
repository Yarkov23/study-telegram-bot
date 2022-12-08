package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.State;
import org.yarkov.entity.Student;
import org.yarkov.entity.StudentTheme;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;
import org.yarkov.service.ThemeService;

import java.util.Optional;

@Component
public class SetThemeCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private StudentService studentService;

    public SetThemeCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        User from = update.getMessage().getFrom();
        Optional<Student> foundedStud = studentService.findByTelegramId(from.getId().intValue());
        Student student = foundedStud.get();

        if (!student.getRole().getRoleName().equals("Teacher")) {
            sendBotMessageService.sendMessage(chatId,
                    "Ця команда не доступна для вас.");
            return;
        }

        student.setState(State.SET_THEME);
        student.setStep("Вибрати студента");
        studentService.save(student);
        sendBotMessageService.sendMessage(chatId,
                "Введіть повне ім'я студента якому треба назначити тему: ");
    }

    @Override
    public String getCommandName() {
        return CommandName.SET_THEME.getCommandName();
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
