package org.yarkov.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.command.CommandContainer;
import org.yarkov.entity.State;

@Component
public class DefaultState implements StateProcess {

    public static String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;

    public DefaultState(CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }

    public State getState() {
        return State.DEFAULT;
    }

    public void process(Update update, String step) {
        String message = update.getMessage().getText().trim();
        if (message.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = message.split(" ")[0].toLowerCase();
            commandContainer.retrieveCommand(commandIdentifier).execute(update);
        }
    }
}
