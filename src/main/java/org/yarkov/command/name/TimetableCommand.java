package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.entity.Timetable;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.TimetableService;

import java.util.List;

public class TimetableCommand implements Command {


    private final SendBotMessageService sendBotMessageService;

    private TimetableService timetableService;

    public TimetableCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }


    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        List<Timetable> timetables = timetableService.findAll();
        StringBuilder stringBuilder = new StringBuilder();

        for (Timetable timetable : timetables) {
            stringBuilder.append(timetable.toString() + "\n");
        }

        sendBotMessageService.sendMessage(chatId, stringBuilder.toString());

    }

    @Autowired
    public void setTimetableService(TimetableService timetableService) {
        this.timetableService = timetableService;
    }
}
