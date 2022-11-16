package org.yarkov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.yarkov.command.CommandContainer;
import org.yarkov.entity.*;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.StudentService;
import org.yarkov.service.StudentThemeService;
import org.yarkov.service.ThemeService;
import org.yarkov.state.StateProcessFactory;

import java.util.Optional;

@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.name}")
    private String botName;

    private StateProcessFactory stateProcessFactory;
    private SendBotMessageService sendBotMessageService;
    private StudentService studentService;

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

            stateProcessFactory.getStateProcess(student.get().getState())
                    .process(update, student.get().getStep());
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

    @Autowired
    public void setStateProcessFactory(StateProcessFactory stateProcessFactory) {
        this.stateProcessFactory = stateProcessFactory;
    }
}
