package io.yye.fsm;

/**
 * 模型接口
 * @param <STATE> 模型所使用的状态类型
 */
public interface IModel<STATE extends IState> {

    /**
     * 获取模型名称
     */
    String getModel();

    /**
     * 获取模型id
     */
    String getModelId();

    /**
     * 获取模型状态
     */
    STATE getState();

    /**
     * 设置模型状态
     */
    void setState(STATE state);

    /**
     * 获取扩展数据
     */
    String getExt();
}
