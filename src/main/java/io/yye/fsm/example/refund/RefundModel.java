package io.yye.fsm.example.refund;

import io.yye.fsm.IModel;
import lombok.Data;

@Data
public class RefundModel implements IModel<RefundState> {
    private String model;
    private String modelId;
    private RefundState state;
    private String ext;

    public RefundModel(String model, String modelId, RefundState state, String ext) {
        this.model = model;
        this.modelId = modelId;
        this.state = state;
        this.ext = ext;
    }
}
