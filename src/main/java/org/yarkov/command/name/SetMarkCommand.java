package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.State;
import org.yarkov.entity.Student;
import org.yarkov.service.SendBotMessageServiceImpl;
import org.yarkov.service.StudentService;

import java.util.Optional;

@Component
public class SetMarkCommand implements Command {

    private final SendBotMessageServiceImpl sendBotMessageService;

    private StudentService studentService;

    public SetMarkCommand(SendBotMessageServiceImpl sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

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

        student.setState(State.SET_MARK);
        student.setStep("Вибрати студента");
        studentService.save(student);
        sendBotMessageService.sendMessage(chatId,
                "Введіть повне ім'я студента якому необхідно поставити оцінку: ");
    }

    public String getCommandName() {
        return CommandName.SET_MARK.getCommandName();
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
