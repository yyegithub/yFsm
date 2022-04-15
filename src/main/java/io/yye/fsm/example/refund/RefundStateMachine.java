package io.yye.fsm.example.refund;

import io.yye.fsm.*;

import java.util.ArrayList;
import java.util.List;

import static io.yye.fsm.example.refund.RefundState.*;
import static io.yye.fsm.example.refund.RefundEvent.*;

public class RefundStateMachine extends AbstractStateMachine<RefundState, RefundEvent, RefundModel> {
    public RefundStateMachine() {
        List<StateTransition<RefundState, RefundEvent, RefundModel>> stateTransitions = new ArrayList<>();

        stateTransitions.add(new StateTransition<>(INIT, REFUND_BEFORE, m -> REFUND_WAIT));
        stateTransitions.add(new StateTransition<>(REFUND_WAIT, REFUND, new RefundHandler()));
        stateTransitions.add(new StateTransition<>(REFUND_APPLIED, REFUND_NOTIFY, new RefundNotifyHandler()));
        stateTransitions.add(new StateTransition<>(REFUND_ABNORMAL, SET_REFUND_MANUAL, new SetRefundManualHandler()));

        initStateTransitions(stateTransitions);
    }

    @Override
    public RefundModel getModel(String model, String modelId) {
        throw new IllegalStateException("method not support");
    }

    public static void main(String[] args) throws Exception{
        RefundModel refundModel = new RefundModel("REFUND", "1", INIT, "");

        RefundStateMachine refundStateMachine = new RefundStateMachine();

        refundStateMachine.doTransition(REFUND_BEFORE, refundModel);
        refundStateMachine.doTransition(REFUND, refundModel);
        refundStateMachine.doTransition(REFUND_NOTIFY, refundModel);
        refundStateMachine.doTransition(SET_REFUND_MANUAL, refundModel);

        refundStateMachine.doTransition(SET_REFUND_MANUAL, refundModel);// will throw FinalStateException
    }
}
