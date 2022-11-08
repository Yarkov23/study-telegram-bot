package org.yarkov.command;

import org.springframework.stereotype.Component;
import org.yarkov.service.SendBotMessageService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandContainer {
    private final Map<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(List<Command> commandList, Command unknownCommand) {

        commandMap = commandList.stream()
                .collect(Collectors.toMap(Command::getCommandName, c -> c));

        this.unknownCommand = unknownCommand;
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}
