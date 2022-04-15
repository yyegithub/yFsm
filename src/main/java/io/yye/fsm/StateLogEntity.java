package io.yye.fsm;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 状态迁移日志实体，持久化时使用
 */
@Data
public class StateLogEntity {
    /**
     * 模型类别
     */
    private String model;
    /**
     * 模型id
     */
    private String modelId;
    /**
     * 迁移前状态
     */
    private String fromState;
    /**
     * 迁移后状态
     */
    private String targetState;
    /**
     * 事件
     */
    private String event;
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
    /**
     * 扩展数据
     */
    private String ext;
}
