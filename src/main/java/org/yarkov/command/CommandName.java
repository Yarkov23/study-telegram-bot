package org.yarkov.command;

public enum CommandName {

    START("/start"),
    TIMETABLE("/timetable"),
    SET_THEME("/set_theme"),
    SET_MARK("/set_mark"),
    SET_SUBMISSION_DAY("/set_submission"),
    SHOW_MARKS("/show_marks"),
    THEMES("/themes"),
    ADD_THEME("/add_theme"),
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
