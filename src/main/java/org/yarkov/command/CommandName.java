package org.yarkov.command;

public enum CommandName {

    START("/start"),
    TIMETABLE("/timetable"),
    SET_THEME("/set_theme"),
    SHOW_MARKS("/show_marks"),
    FIND_THEME("/find_theme"),
    THEMES("/themes"),

    MY_MARK("/my_mark"),
    MY_THEME("/my_theme"),
    SHOW_THEMES("/show_themes"),
    HELP("/help");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
