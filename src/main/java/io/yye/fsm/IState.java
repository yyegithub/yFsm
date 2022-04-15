package io.yye.fsm;

/**
 * 状态接口
 */
public interface IState {
    /**
     * 获取状态名称
     */
    String getState();

    /**
     * 是否终态
     */
    boolean isFinalState();
}
