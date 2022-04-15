package fun.ycx.fsm.example.refund;

import java.util.function.Function;

public class RefundHandler implements Function<RefundModel, RefundState> {
    @Override
    public RefundState apply(RefundModel refundModel) {
        return RefundState.REFUND_APPLIED;
    }
}
