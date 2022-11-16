package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.Subject;
import org.yarkov.entity.Theme;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.ThemeService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

        stringBuilder.append("Перелік тем: " + "\n\n");

        Map<Subject, List<Theme>> classificationBySubject = themes.stream().
                collect(Collectors.groupingBy(Theme::getSubjectId));

        AtomicInteger counter = new AtomicInteger();

        classificationBySubject.forEach((subject, themeList) -> {
            stringBuilder.append(subject.getCaption() + "\n");

            for (Theme theme : themeList) {
                stringBuilder.append(counter +". "+ theme.getCaption() + "\n");
                counter.getAndIncrement();
            }

            stringBuilder.append("\n");
        });

/*        for (Theme theme : themes) {
            String subjectCaption = theme.getSubjectId().getCaption();
            String themeCaption = theme.getCaption();

            stringBuilder.append(
                    "Предмет: " + subjectCaption + "\n" +
                            "Назва теми: " + themeCaption + "\n\n"
            );
        }*/

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
