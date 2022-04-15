package io.yye.fsm.example.refund;

import java.util.function.Function;

public class RefundNotifyHandler implements Function<RefundModel, RefundState> {
    @Override
    public RefundState apply(RefundModel refundModel) {
        return RefundState.REFUND_ABNORMAL;
    }
}
