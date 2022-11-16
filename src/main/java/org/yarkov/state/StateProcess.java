package org.yarkov.state;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.yarkov.entity.State;

public interface StateProcess {

    State getState();

    void process(Update update, String step);

}
