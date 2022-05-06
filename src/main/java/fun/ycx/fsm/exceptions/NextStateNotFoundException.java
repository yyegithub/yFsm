package fun.ycx.fsm.exceptions;

import fun.ycx.fsm.IEvent;
import fun.ycx.fsm.IState;
import lombok.Getter;

@Getter
public class NextStateNotFoundException extends Exception{
    private final IState state;
    private final IEvent event;

    public NextStateNotFoundException(IState state, IEvent event) {
        super("can't find state transition on state "+state.getState()+" with event "+event.getEvent());
        this.state = state;
        this.event = event;
    }
}
