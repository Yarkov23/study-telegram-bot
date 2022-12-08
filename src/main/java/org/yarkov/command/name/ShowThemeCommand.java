package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.StudentTheme;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.StudentThemeService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ShowThemeCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private StudentThemeService studentThemeService;

    public ShowThemeCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        StringBuilder stringBuilder = new StringBuilder();
        List<StudentTheme> studentThemes = studentThemeService.findAll();

        if (!studentThemes.isEmpty()) {
            Map<String, List<StudentTheme>> classificationByGroup = studentThemes.stream().
                    collect(Collectors.groupingBy(studentTheme -> studentTheme.getStudentId().getGroup()));

            AtomicInteger counter = new AtomicInteger(1);

            classificationByGroup.forEach((group, studentThemeList) -> {
                stringBuilder.append("<b>" + group + "</b>" + ":" + "\n");

                for (StudentTheme studentTheme : studentThemes) {
                    String fullName = studentTheme.getStudentId().getFullName();
                    String caption = studentTheme.getThemeId().getCaption();
                    stringBuilder.append(counter.getAndIncrement() + ". " + fullName + " - " + caption + "\n");
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
        return CommandName.SHOW_THEMES.getCommandName();
    }

    @Autowired
    public void setStudentThemeService(StudentThemeService studentThemeService) {
        this.studentThemeService = studentThemeService;
    }
}
