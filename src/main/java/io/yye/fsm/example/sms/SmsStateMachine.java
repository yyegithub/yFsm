package io.yye.fsm.example.sms;

import io.yye.fsm.*;

import java.util.ArrayList;
import java.util.List;

public class SmsStateMachine extends AbstractStateMachine<DefaultState, DefaultEvent, DefaultModel> {
    static final DefaultState initState = new DefaultState("INIT", false);
    static final DefaultState sentState = new DefaultState("SENT", false);
    static final DefaultState successState = new DefaultState("SUCCESS", true);
    static final DefaultState failState = new DefaultState("FAIL", true);
    static final DefaultState waitRetryState = new DefaultState("WAITRETRY", false);

    static final DefaultEvent sendEvent = new DefaultEvent("SEND");
    static final DefaultEvent successEvent = new DefaultEvent("SUCCESS");
    static final DefaultEvent failEvent = new DefaultEvent("FAIL");
    static final DefaultEvent waitRetryEvent = new DefaultEvent("WAIT_RETRY");

    public SmsStateMachine() {
        List<StateTransition<DefaultState, DefaultEvent, DefaultModel>> stateTransitions = new ArrayList<>();

        stateTransitions.add(new StateTransition<>(initState, sendEvent, m-> sentState));
        stateTransitions.add(new StateTransition<>(sentState, successEvent, m-> successState));
        stateTransitions.add(new StateTransition<>(sentState, failEvent, m-> failState));
        stateTransitions.add(new StateTransition<>(sentState, waitRetryEvent, m-> waitRetryState));
        stateTransitions.add(new StateTransition<>(waitRetryState, sendEvent, m-> sentState));

        initStateTransitions(stateTransitions);
    }

    public static void main(String[] args) throws Exception{
        DefaultModel smsModel = new DefaultModel("SMS", "1", initState, "");
        SmsStateMachine smsStateMachine = new SmsStateMachine();

        smsStateMachine.doTransition(sendEvent, smsModel);

        for (int i=0;i<2;i++) {
            smsStateMachine.doTransition(waitRetryEvent, smsModel);
            smsStateMachine.doTransition(sendEvent, smsModel);
        }

        //smsStateMachine.doTransition(sendEvent, smsModel);//will throw NextStateNotFoundException

        smsStateMachine.doTransition(successEvent, smsModel);
        //smsStateMachine.doTransition(sendEvent, smsModel);// will throw FinalStateException

    }

    @Override
    public DefaultModel getModel(String model, String modelId) {
        throw new IllegalStateException("method not support");
    }
}
