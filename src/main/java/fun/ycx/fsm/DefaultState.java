package fun.ycx.fsm;

import lombok.Data;

@Data
public class DefaultState implements IState {
    private String state;
    private boolean finalState;

    public DefaultState(String state, boolean finalState) {
        this.state = state;
        this.finalState = finalState;
    }
}
