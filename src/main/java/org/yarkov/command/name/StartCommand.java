package org.yarkov.command.name;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.service.SendBotMessageService;

@Component
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Початок роботи.";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public String getCommandName() {
        return CommandName.START.getCommandName();
    }


    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
