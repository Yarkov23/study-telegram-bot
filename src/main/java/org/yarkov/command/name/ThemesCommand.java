package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.Student;
import org.yarkov.entity.Subject;
import org.yarkov.entity.Theme;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.StudentService;
import org.yarkov.service.ThemeService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ThemesCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private StudentService studentService;
    private ThemeService themeService;

    public ThemesCommand(SendBotMessageService sendBotMessageService) {
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

        List<Theme> themes = themeService.findAll();

        if (!themes.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<b>Перелік тем:</b> " + "\n\n");

            Map<Subject, List<Theme>> classificationBySubject = themes.stream().
                    collect(Collectors.groupingBy(Theme::getSubjectId));

            AtomicInteger counter = new AtomicInteger();

            classificationBySubject.forEach((subject, themeList) -> {
                stringBuilder.append("<b>" + subject.getCaption() + "</b>" + "\n");

                for (Theme theme : themeList) {
                    stringBuilder.append(counter + ". " + theme.getCaption() + "\n");
                    counter.getAndIncrement();
                }

                stringBuilder.append("\n");
            });

            sendBotMessageService.sendMessage(chatId, stringBuilder.toString());
        } else {
            sendBotMessageService.sendMessage(chatId, "Дані відсутні.");
        }
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String getCommandName() {
        return CommandName.THEMES.getCommandName();
    }

    @Autowired
    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }
}
