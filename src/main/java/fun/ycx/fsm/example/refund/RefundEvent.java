package fun.ycx.fsm.example.refund;

import fun.ycx.fsm.IEvent;

public enum RefundEvent implements IEvent {
    REFUND_BEFORE,
    REFUND,
    SET_REFUND_MANUAL,
    REFUND_QUERY,
    REFUND_NOTIFY,
    UPDATE_REFUND_STATUS,
    ;

    @Override
    public String getEvent() {
        return name();
    }
}
