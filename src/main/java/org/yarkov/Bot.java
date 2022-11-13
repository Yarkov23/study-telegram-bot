package org.yarkov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.CommandContainer;
import org.yarkov.entity.Student;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.SendBotMessageServiceImpl;
import org.yarkov.service.StudentService;

import java.util.Optional;

@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.name}")
    private String botName;

    public static String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;

    private SendBotMessageService sendBotMessageService;
    private StudentService studentService;

    public Bot(CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotUsername() {
        return botName;
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            User from = update.getMessage().getFrom();

            Optional<Student> student = studentService.findByTelegramId(from.getId().intValue());

            if (student.isEmpty()) {
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                        "Користування ботом дозволено тільки студентам та вчителям КФКТЕ НАУ");
                return;
            }


            switch (student.get().getState()) {
                case SET_MARK -> {

                }
                case ADD_THEME -> {

                }
                case SET_THEME -> {

                }
                case DEFAULT -> {

                }
            }


            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            }


        }
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setSendBotMessageService(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
}
