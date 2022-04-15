package fun.ycx.fsm;

import lombok.Data;

@Data
public class DefaultEvent implements IEvent {
    private String event;

    public DefaultEvent(String event) {
        this.event = event;
    }
}
