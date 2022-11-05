package org.yarkov.command;

import com.google.common.collect.ImmutableMap;
import org.yarkov.command.name.HelpCommand;
import org.yarkov.command.name.StartCommand;
import org.yarkov.command.name.TimetableCommand;
import org.yarkov.command.name.UnknownCommand;
import org.yarkov.service.SendBotMessageService;

import static org.yarkov.command.CommandName.*;

public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;


    public CommandContainer(SendBotMessageService sendBotMessageService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(TIMETABLE.getCommandName(), new TimetableCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}
