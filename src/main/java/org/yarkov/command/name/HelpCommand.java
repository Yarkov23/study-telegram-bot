package org.yarkov.command.name;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.Command;
import org.yarkov.service.SendBotMessageService;

import static org.yarkov.command.CommandName.*;

@Component
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public static final String HELP_MESSAGE = String.format("✨<b>Доступні команди</b>✨\n\n"
                    + "<b>Загальні команди:</b> \n"
                    + "%s - Почати роботу\n"
                    + "%s - Отримати перелік команд\n\n"
                    + "<b>Команди для студентів:</b> \n"
                    + "%s - Отримати свою оцінку\n"
                    + "%s - Отримати свою тему\n"
                    + "%s - Отримати інформаційне табло\n\n"
                    + "<b>Команди для вчителів:</b> \n"
                    + "%s - Отримати перелік оцінок\n"
                    + "%s - Отримати перелік студентів і їх тем\n"
                    + "%s - Отримати перелік доступних тем\n"
                    + "%s - Додати нову тему\n"
                    + "%s - Назначити тему студенту\n"
                    + "%s - Поставити оцінку студенту\n"
                    + "%s - Назначити дату здачі студенту\n",
            START.getCommandName(), HELP.getCommandName(), MY_MARK.getCommandName(), MY_THEME.getCommandName(),
            TIMETABLE.getCommandName(), SHOW_MARKS.getCommandName(), SHOW_THEMES.getCommandName(), THEMES.getCommandName(),
            ADD_THEME.getCommandName(), SET_THEME.getCommandName(), SET_MARK.getCommandName(), SET_SUBMISSION_DAY.getCommandName());

    @Override
    public String getCommandName() {
        return HELP.getCommandName();
    }

    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
