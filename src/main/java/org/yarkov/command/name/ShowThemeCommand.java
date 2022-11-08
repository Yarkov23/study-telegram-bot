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

        for (StudentTheme studentTheme : studentThemes) {
            String fullName = studentTheme.getStudentId().getFullName();
            String caption = studentTheme.getThemeId().getCaption();
            stringBuilder.append(fullName + " - " + caption + "\n\n");
        }

        sendBotMessageService.sendMessage(chatId, stringBuilder.toString());

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
