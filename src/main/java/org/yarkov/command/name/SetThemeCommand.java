package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
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


    private static final String START_SETTING_TEXT =
            "Введіть повне ім'я студента якому треба змінити/назначити тему.";



    public SetThemeCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chatId, START_SETTING_TEXT);
        String fullName = update.getMessage().getText();
    }

    @Override
    public String getCommandName() {
        return CommandName.SET_THEME.getCommandName();
    }


}
