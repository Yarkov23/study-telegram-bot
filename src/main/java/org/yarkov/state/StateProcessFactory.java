package org.yarkov.state;

import org.springframework.stereotype.Component;
import org.yarkov.entity.State;

import java.util.List;

@Component
public class StateProcessFactory {

    private List<StateProcess> stateProcessList;


    public StateProcessFactory(List<StateProcess> stateProcessList) {
        this.stateProcessList = stateProcessList;
    }

    public StateProcess getStateProcess(State state) {
        for (StateProcess stateProcess : stateProcessList) {
            if (stateProcess.getState().equals(state)) {
                return stateProcess;
            }
        }
        return null;
    }

}
