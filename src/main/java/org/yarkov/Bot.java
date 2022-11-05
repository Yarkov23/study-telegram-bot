package org.yarkov;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.CommandContainer;
import org.yarkov.service.SendBotMessageServiceImpl;

@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.name}")
    private String botName;

    public static String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;

    public Bot() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotUsername() {
        return botName;
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            }
        }
    }
}
