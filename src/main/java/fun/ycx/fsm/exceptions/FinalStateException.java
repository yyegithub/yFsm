package fun.ycx.fsm.exceptions;

import fun.ycx.fsm.IState;
import lombok.Getter;

@Getter
public class FinalStateException extends Exception{
    private final IState state;

    public FinalStateException(IState state) {
        super(state.getState()+" is final state, can't do transition on it");
        this.state = state;
    }
}
