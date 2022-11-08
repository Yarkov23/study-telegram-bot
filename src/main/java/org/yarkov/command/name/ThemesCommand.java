package org.yarkov.command.name;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.Theme;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.ThemeService;

import java.util.List;

@Component
public class ThemesCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private ThemeService themeService;

    public ThemesCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        List<Theme> themes = themeService.findAll();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Перелік доступних тем: " + "\n\n");

        for (Theme theme : themes) {
            String subjectCaption = theme.getSubjectId().getCaption();
            String themeCaption = theme.getCaption();

            stringBuilder.append(
                    "Предмет: " + subjectCaption + "\n" +
                            "Назва теми: " + themeCaption + "\n\n"
            );
        }

        sendBotMessageService.sendMessage(chatId, stringBuilder.toString());

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
