package fun.ycx.fsm;

import lombok.Data;

import java.util.function.Function;

/**
 * 状态迁移
 * @param <STATE> 状态类型
 * @param <EVENT> 事件类型
 * @param <MODEL> 模型类型
 */
@Data
public class StateTransition<STATE extends IState, EVENT extends IEvent, MODEL extends IModel<STATE>> {
    /**
     * 进入状态
     */
    private STATE fromState;
    /**
     * 事件
     */
    private EVENT event;
    /**
     * 处理方法，根据模型进行处理，返回迁移到的状态
     */
    private Function<MODEL, STATE> handler;

    public StateTransition(STATE fromState, EVENT event, Function<MODEL, STATE> handler) {
        this.fromState = fromState;
        this.event = event;
        this.handler = handler;
    }

    public STATE toNextState(MODEL model) {
        return handler.apply(model);
    }
}
