package io.yye.fsm;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 模型状态实体，持久化时使用
 */
@Data
public class ModelStateEntity {
    /**
     * 模型类别
     */
    private String model;
    /**
     * 模型id
     */
    private String modelId;
    /**
     * 状态名称
     */
    private String state;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime modifyTime;
}
