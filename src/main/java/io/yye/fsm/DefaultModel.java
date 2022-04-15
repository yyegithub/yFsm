package io.yye.fsm;

import lombok.Data;

@Data
public class DefaultModel implements IModel<DefaultState> {
    private String model;
    private String modelId;
    private DefaultState state;
    private String ext;

    public DefaultModel(String model, String modelId, DefaultState state, String ext) {
        this.model = model;
        this.modelId = modelId;
        this.state = state;
        this.ext = ext;
    }
}
