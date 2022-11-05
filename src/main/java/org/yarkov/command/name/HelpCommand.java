package org.yarkov.command.name;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.service.SendBotMessageService;

import static org.yarkov.command.CommandName.HELP;
import static org.yarkov.command.CommandName.START;

public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Доступні команди</b>✨\n\n"
                    + "%s - Почати роботу\n"
                    + "%s - Отримати перелік команд\n",
            START.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }


    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
