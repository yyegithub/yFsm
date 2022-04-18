package fun.ycx.fsm;

import lombok.extern.slf4j.Slf4j;

import javax.management.modelmbean.ModelMBean;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象的状态机，定义了初始化及状态迁移到过程
 * @param <STATE> 状态类型
 * @param <EVENT> 事件类型
 * @param <MODEL> 模型类型
 */
@Slf4j
public abstract class AbstractStateMachine<STATE extends IState, EVENT extends IEvent, MODEL extends IModel<STATE>> {
    private final Map<STATE, Map<EVENT, StateTransition<STATE, EVENT, MODEL>>> stateTransitionMap = new HashMap<>();

    /**
     * 初始化方法
     * @param stateTransitionMap 流转规则map
     */
    public void initStateTransitions(Map<STATE, Map<EVENT, StateTransition<STATE, EVENT, MODEL>>> stateTransitionMap) {
        this.stateTransitionMap.clear();
        this.stateTransitionMap.putAll(stateTransitionMap);
    }

    /**
     * 初始化方法
     * @param stateTransitions 流转规则list
     */
    public void initStateTransitions(List<StateTransition<STATE, EVENT, MODEL>> stateTransitions) {
        this.stateTransitionMap.clear();
        for (StateTransition<STATE, EVENT, MODEL> stateTransition: stateTransitions) {
            Map<EVENT, StateTransition<STATE, EVENT, MODEL>> map = stateTransitionMap.get(stateTransition.getFromState());
            if(map == null)  map = new HashMap<>();
            map.put(stateTransition.getEvent(), stateTransition);

            this.stateTransitionMap.put(stateTransition.getFromState(), map);
        }
    }

    /**
     * 状态迁移，可根据模型id从重载的getModel中获取模型
     * @param event 事件
     * @param model 模型类别
     * @param modelId 模型id
     * @return 状态迁移后的模型
     * @throws FinalStateException 模型状态为终态时抛出
     * @throws NextStateNotFoundException 根据模型当前状态及事件，没有对应的状态迁移处理对象时抛出
     */
    public MODEL doTransition(EVENT event, String model, String modelId) throws FinalStateException, NextStateNotFoundException  {
        MODEL m = getModel(model, modelId);
        return doTransition(event, m);
    }

    /**
     * 状态迁移
     * @param event 事件
     * @param model 模型
     * @return 状态迁移后的模型
     * @throws FinalStateException 模型状态为终态时抛出
     * @throws NextStateNotFoundException 根据模型当前状态及事件，没有对应的状态迁移处理对象时抛出
     */
    public MODEL doTransition(EVENT event, MODEL model) throws FinalStateException, NextStateNotFoundException {
        if(stateTransitionMap.size()<1) {
            throw new IllegalStateException("stateTransitionMap must be init first");
        }
        if(!checkParam(event, model)) {
            throw new IllegalArgumentException("check param failed");
        }

        if(model.getState().isFinalState()) {
            throw new FinalStateException(model.getState());
        }

        try {
            if (tryLock(model)) {
                beforeTransition(event, model);

                StateTransition<STATE, EVENT, MODEL> matchingStateTransition = getStateTransition(model, event);
                STATE nextState = matchingStateTransition.toNextState(model);
                model.setState(nextState);

                postTransition(event, model);

                ModelStateEntity modelStateEntity = new ModelStateEntity();
                modelStateEntity.setModel(model.getModel());
                modelStateEntity.setModelId(model.getModelId());
                modelStateEntity.setState(nextState.getState());

                saveOrUpdateModelStateEntity(modelStateEntity);

                StateLogEntity stateLogEntity = new StateLogEntity();
                stateLogEntity.setModel(model.getModel());
                stateLogEntity.setModelId(model.getModelId());
                stateLogEntity.setFromState(model.getState().getState());
                stateLogEntity.setTargetState(nextState.getState());
                stateLogEntity.setEvent(event.getEvent());
                stateLogEntity.setExt(model.getExt());
                stateLogEntity.setEventTime(LocalDateTime.now());
                saveStateLogEntity(stateLogEntity);

                return model;
            } else {
                throw new IllegalStateException("failed when try to lock " + model);
            }
        } finally {
            releaseLock(model);
        }
    }

    /**
     * 根据模型和事件，获取对应的迁移处理对象
     * @param model 模型
     * @param event 事件
     * @return 迁移处理对象
     * @throws NextStateNotFoundException 根据模型当前状态及事件，没有对应的状态迁移处理对象时抛出
     */
    public StateTransition<STATE, EVENT, MODEL> getStateTransition(MODEL model, EVENT event) throws NextStateNotFoundException {
        StateTransition<STATE, EVENT, MODEL> matchingStateTransition;
        Map<EVENT, StateTransition<STATE, EVENT, MODEL>> stateMap = stateTransitionMap.get(model.getState());
        if(stateMap == null || stateMap.isEmpty() || (matchingStateTransition = stateMap.get(event)) == null) {
            throw new NextStateNotFoundException(model.getState(), event);
        }
        return matchingStateTransition;
    }

    /**
     * 参数校验，子类可重载实现
     * @param event 事件
     * @param model 模型
     * @return 校验是否通过
     */
    public boolean checkParam(EVENT event, MODEL model) {
        log.debug("checkParam"+event+model);
        return true;
    }

    /**
     * 执行状态迁移前的钩子方法，子类可重载自定义
     * @param event 事件
     * @param model 模型
     */
    public void beforeTransition(EVENT event, MODEL model) {
        log.debug("before transition"+event+model);
    }

    /**
     * 执行状态迁移后的钩子方法，子类可重载自定义
     * @param event 事件
     * @param model 模型
     */
    public void postTransition(EVENT event, MODEL model) {
        log.debug("post transition"+event+model);
    }

    /**
     * 根据模型类别及id获取模型对象
     * @param model 模型类别
     * @param modelId 模型id
     * @return 模型
     */
    public abstract MODEL getModel(String model, String modelId);

    /**
     * 模型实体保存方法，子类根据选定的持久化方案重载
     * @param entity 要保存的模型实体
     */
    protected void saveOrUpdateModelStateEntity(ModelStateEntity entity) {
        log.debug("save or update model "+entity);
    }

    /**
     * 状态迁移日志保存方法，子类根据选定的持久化方案重载
     * @param entity 要保存的迁移日志实体
     */
    protected void saveStateLogEntity(StateLogEntity entity) {
        log.debug("save state log "+entity);
    }

    /**
     * 针对model加锁， 可自定义分布式锁
     * @param model 模型
     * @return 加锁结果
     */
    protected boolean tryLock(MODEL model) {
        return true;
    }

    /**
     * 释放锁
     * @param model 模型
     */
    protected void releaseLock(MODEL model) {

    }
}
