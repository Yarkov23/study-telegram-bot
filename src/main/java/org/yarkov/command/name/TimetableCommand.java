package org.yarkov.command.name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.command.CommandName;
import org.yarkov.entity.Timetable;
import org.yarkov.service.SendBotMessageService;
import org.yarkov.service.TimetableService;

import java.util.List;

@Component
public class TimetableCommand implements Command {


    private final SendBotMessageService sendBotMessageService;

    private TimetableService timetableService;

    private final SendBotMessageService sendBotMessageService;

    public TimetableCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public String getCommandName() {
        return CommandName.TIMETABLE.getCommandName();
    }

    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        StringBuilder stringBuilder = new StringBuilder();


        List<Timetable> timetables = timetableService.findAll();


        if (!timetables.isEmpty()) {
            for (Timetable timetable : timetables) {
                String themeCaption = timetable.getStudentTheme().getThemeId().getCaption();
                String studentName = timetable.getStudentTheme().getStudentId().getFullName();
                String date = timetable.getDate().toString();

                stringBuilder.append(
                        "Студент: " + studentName + "\n"
                                + "Назва теми: " + themeCaption + "\n"
                                + "Дата здачі робіт: " + date + "\n\n"
                );
            }
            sendBotMessageService.sendMessage(chatId, stringBuilder.toString());
        } else {
            sendBotMessageService.sendMessage(chatId, "Дані відсутні.");
        }


    }

    @Autowired
    public void setTimetableService(TimetableService timetableService) {
        this.timetableService = timetableService;
    }
}
