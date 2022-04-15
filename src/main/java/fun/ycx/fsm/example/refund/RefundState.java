package fun.ycx.fsm.example.refund;

import fun.ycx.fsm.IState;

public enum RefundState implements IState {
    INIT,
    REFUND_WAIT,
    REFUND_SUCCESS(true),
    REFUND_PEND,
    REFUND_APPLIED,
    REFUND_MANUAL(true),
    REFUND_FAIL,
    REFUND_CANCEL,
    REFUND_ABNORMAL,
    REFUND_MANUAL_FROM_ABNORMAL(true),
    REFUND_SUCCESS_FROM_ABNORMAL(true),
    ;

    RefundState() {
        this.finalState = false;
    }

    RefundState(boolean finalState) {
        this.finalState = finalState;
    }

    private final boolean finalState;

    @Override
    public String getState() {
        return name();
    }

    @Override
    public boolean isFinalState() {
        return finalState;
    }
}
