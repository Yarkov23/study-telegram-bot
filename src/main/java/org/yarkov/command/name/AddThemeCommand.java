package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.State;
import org.yarkov.entity.Student;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.StudentService;

import java.util.Optional;

@Component
public class AddThemeCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private StudentService studentService;

    public AddThemeCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        User from = update.getMessage().getFrom();
        Optional<Student> foundedStudent = studentService.findByTelegramId(from.getId().intValue());
        Student student = foundedStudent.get();
        student.setState(State.ADD_THEME);
        student.setStep("Ввести назву теми");
        studentService.save(student);
        sendBotMessageService.sendMessage(chatId, "Введіть назву теми яку хочете додати: ");
    }


    public String getCommandName() {
        return CommandName.ADD_THEME.getCommandName();
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
