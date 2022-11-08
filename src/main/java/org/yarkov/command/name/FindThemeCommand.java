package org.yarkov.command.name;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.service.SendBotMessageService;

@Component
public class FindThemeCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public FindThemeCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
    }

    @Override
    public String getCommandName() {
        return CommandName.FIND_THEME.getCommandName();
    }
}
