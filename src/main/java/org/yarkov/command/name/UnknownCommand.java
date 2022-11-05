package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.service.SendBotMessageService;

public class UnknownCommand implements Command {

    public static final String UNKNOWN_MESSAGE = "Для того щоб дізнатися перелік команд\n" +
            "1. Введіть команду /help\n" +
            "2. Подивіться доступні команди в меню";

    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chatId, UNKNOWN_MESSAGE);
    }
}
