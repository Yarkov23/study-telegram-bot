package org.yarkov.command;

public enum CommandName {

    START("/start"),
    TIMETABLE("/timetable"),
    HELP("/help");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
