package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.Student;
import org.yarkov.entity.StudentTheme;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        String chatId = update.getMessage().getChatId().toString();
        User from = update.getMessage().getFrom();
        Optional<Student> foundedStud = studentService.findByTelegramId(from.getId().intValue());
        Student student = foundedStud.get();

        if (!student.getRole().getRoleName().equals("Teacher")) {
            sendBotMessageService.sendMessage(chatId,
                    "Ця команда не доступна для вас.");
            return;
        }

        List<StudentTheme> studentThemes = studentThemeService.findAll();

        if (!studentThemes.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<b>Журнал оцінок:</b> " + "\n");

            Map<String, List<StudentTheme>> classificationByGroup = studentThemes.stream().
                    collect(Collectors.groupingBy(studentTheme -> studentTheme.getStudentId().getGroup()));

            AtomicInteger counter = new AtomicInteger(1);

            classificationByGroup.forEach((group, studentThemeList) -> {
                stringBuilder.append("\n" + "<b>" + group + "</b>" + ":" + "\n");

                for (StudentTheme studentTheme : studentThemes) {
                    String fullName = studentTheme.getStudentId().getFullName();
                    String mark = String.valueOf(studentTheme.getMark());
                    stringBuilder.append(counter.getAndIncrement() + ". " + fullName + " - " + mark + "\n");
                }

                stringBuilder.append("\n");
            });

            sendBotMessageService.sendMessage(chatId, stringBuilder.toString());
        } else {
            sendBotMessageService.sendMessage(chatId, "Дані відсутні.");
        }
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
