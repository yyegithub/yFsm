package io.yye.fsm.example.refund;

import java.util.function.Function;

public class SetRefundManualHandler implements Function<RefundModel, RefundState> {
    @Override
    public RefundState apply(RefundModel refundModel) {
        return RefundState.REFUND_MANUAL_FROM_ABNORMAL;
    }
}
