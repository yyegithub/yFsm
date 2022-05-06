package fun.ycx.fsm.example.refund;

import fun.ycx.fsm.*;

import java.util.ArrayList;
import java.util.List;

import static fun.ycx.fsm.example.refund.RefundState.*;
import static fun.ycx.fsm.example.refund.RefundEvent.*;

public class RefundStateMachine extends AbstractStateMachine<RefundState, RefundEvent, RefundModel> {
    public RefundStateMachine() {
        List<StateTransition<RefundState, RefundEvent, RefundModel>> stateTransitions = new ArrayList<>();

        stateTransitions.add(new StateTransition<>(INIT, REFUND_BEFORE, m -> REFUND_WAIT, REFUND_WAIT));
        stateTransitions.add(new StateTransition<>(REFUND_WAIT, REFUND, new RefundHandler(), REFUND_APPLIED,REFUND_SUCCESS,REFUND_PEND,REFUND_FAIL));
        stateTransitions.add(new StateTransition<>(REFUND_APPLIED, REFUND_NOTIFY, new RefundNotifyHandler(), REFUND_SUCCESS,REFUND_PEND,REFUND_FAIL,REFUND_ABNORMAL));
        stateTransitions.add(new StateTransition<>(REFUND_ABNORMAL, SET_REFUND_MANUAL, new SetRefundManualHandler(), REFUND_MANUAL_FROM_ABNORMAL));

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
