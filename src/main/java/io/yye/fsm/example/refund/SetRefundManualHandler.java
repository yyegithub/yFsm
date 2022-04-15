package io.yye.fsm.example.refund;

import io.yye.fsm.DefaultModel;
import io.yye.fsm.DefaultState;

import java.util.function.Function;

public class SetRefundManualHandler implements Function<DefaultModel, DefaultState> {
    @Override
    public DefaultState apply(DefaultModel defaultModel) {
        return RefundStateMachine.REFUND_MANUAL_FROM_ABNORMAL;
    }
}
