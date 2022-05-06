package fun.ycx.fsm.exceptions;

import fun.ycx.fsm.IEvent;
import fun.ycx.fsm.IModel;
import fun.ycx.fsm.IState;
import lombok.Getter;

@Getter
@SuppressWarnings("rawtypes")
public class InvalidStateException extends Exception{
    private final IState fromState;
    private final IState targetState;
    private final IEvent event;
    private final IModel model;

    public InvalidStateException(IState fromState, IState targetState, IEvent event, IModel model) {
        super("invalid target state "+targetState.getState()+" from state "+fromState.getState()+" with event "+event.getEvent()+" and model "+model.getModel());
        this.fromState = fromState;
        this.targetState = targetState;
        this.event = event;
        this.model = model;
    }
}
