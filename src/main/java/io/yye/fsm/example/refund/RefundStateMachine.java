package io.yye.fsm.example.refund;

import io.yye.fsm.*;

import java.util.ArrayList;
import java.util.List;

public class RefundStateMachine extends AbstractStateMachine<DefaultState, DefaultEvent, DefaultModel> {
    static final DefaultState INIT = new DefaultState("INIT", false);
    static final DefaultState REFUND_WAIT= new DefaultState("REFUND_WAIT", false);
    static final DefaultState REFUND_SUCCESS = new DefaultState("REFUND_SUCCESS", true);
    static final DefaultState REFUND_PEND = new DefaultState("REFUND_PEND", false);
    static final DefaultState REFUND_APPLIED = new DefaultState("REFUND_APPLIED", false);
    static final DefaultState REFUND_MANUAL = new DefaultState("REFUND_MANUAL", true);
    static final DefaultState REFUND_FAIL = new DefaultState("REFUND_FAIL", false);
    static final DefaultState REFUND_CANCEL = new DefaultState("REFUND_CANCEL", false);
    static final DefaultState REFUND_ABNORMAL = new DefaultState("REFUND_ABNORMAL", false);
    static final DefaultState REFUND_MANUAL_FROM_ABNORMAL = new DefaultState("REFUND_MANUAL_FROM_ABNORMAL", true);
    static final DefaultState REFUND_SUCCESS_FROM_ABNORMAL = new DefaultState("REFUND_SUCCESS_FROM_ABNORMAL", true);

    static final DefaultEvent E_REFUND_BEFORE = new DefaultEvent("REFUND_BEFORE");
    static final DefaultEvent E_REFUND = new DefaultEvent("REFUND");
    static final DefaultEvent E_SET_REFUND_MANUAL = new DefaultEvent("SET_REFUND_MANUAL");
    static final DefaultEvent E_REFUND_QUERY = new DefaultEvent("REFUND_QUERY");
    static final DefaultEvent E_REFUND_NOTIFY = new DefaultEvent("REFUND_QUERY");
    static final DefaultEvent E_UPDATE_REFUND_STATUS = new DefaultEvent("UPDATE_REFUND_STATUS");

    public RefundStateMachine() {
        List<StateTransition<DefaultState, DefaultEvent, DefaultModel>> stateTransitions = new ArrayList<>();

        stateTransitions.add(new StateTransition<>(INIT, E_REFUND_BEFORE, m -> REFUND_WAIT));
        stateTransitions.add(new StateTransition<>(REFUND_WAIT, E_REFUND, new RefundHandler()));
        stateTransitions.add(new StateTransition<>(REFUND_APPLIED, E_REFUND_NOTIFY, new RefundNotifyHandler()));
        stateTransitions.add(new StateTransition<>(REFUND_ABNORMAL, E_SET_REFUND_MANUAL, new SetRefundManualHandler()));

        initStateTransitions(stateTransitions);
    }

    @Override
    public DefaultModel getModel(String model, String modelId) {
        throw new IllegalStateException("method not support");
    }

    public static void main(String[] args) throws Exception{
        DefaultModel refundModel = new DefaultModel("REFUND", "1", INIT, "");

        RefundStateMachine refundStateMachine = new RefundStateMachine();

        refundStateMachine.doTransition(E_REFUND_BEFORE, refundModel);
        refundStateMachine.doTransition(E_REFUND, refundModel);
        refundStateMachine.doTransition(E_REFUND_NOTIFY, refundModel);
        refundStateMachine.doTransition(E_SET_REFUND_MANUAL, refundModel);

        refundStateMachine.doTransition(E_SET_REFUND_MANUAL, refundModel);// will throw FinalStateException
    }
}
