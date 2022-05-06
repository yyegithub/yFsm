package fun.ycx.fsm;

import fun.ycx.fsm.exceptions.InvalidStateException;
import fun.ycx.fsm.util.ConvenientSet;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
     * 将迁移到的状态列表，作为handler处理结果的校验
     */
    private Set<STATE> targetStates;
    /**
     * 事件
     */
    private EVENT event;
    /**
     * 处理方法，根据模型进行处理，返回迁移到的状态
     */
    private Function<MODEL, STATE> handler;

    public StateTransition(STATE fromState, EVENT event, Function<MODEL, STATE> handler) {
        this(fromState, event, handler, ConvenientSet.empty());
    }

    public StateTransition(STATE fromState, EVENT event, Function<MODEL, STATE> handler, STATE targetState) {
        this(fromState, event, handler, ConvenientSet.of(targetState));
    }

    @SafeVarargs
    public StateTransition(STATE fromState, EVENT event, Function<MODEL, STATE> handler, STATE... targetStates) {
        this(fromState, event, handler, ConvenientSet.of(targetStates));
    }

    public StateTransition(STATE fromState, EVENT event, Function<MODEL, STATE> handler, Collection<STATE> targetStates) {
        this(fromState, event, handler, ConvenientSet.of(targetStates));
    }

    public StateTransition(STATE fromState, EVENT event, Function<MODEL, STATE> handler, Set<STATE> targetStates) {
        this.fromState = fromState;
        this.event = event;
        this.handler = handler;
        this.targetStates = targetStates;
    }

    public STATE toNextState(MODEL model) throws InvalidStateException {
        STATE targetState = handler.apply(model);
        if(targetStates == null || targetStates.isEmpty() || targetStates.contains(targetState)) {
            return targetState;
        } else {
            throw new InvalidStateException(fromState, targetState, event, model);
        }
    }
}
