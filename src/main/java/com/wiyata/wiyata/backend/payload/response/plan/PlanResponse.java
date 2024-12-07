package com.wiyata.wiyata.backend.payload.response.plan;

import com.wiyata.wiyata.backend.constant.PlanConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {

    private int status;
    private String message;
    private Object result;

    public PlanResponse successCreatePlan(Object savedPlan) {
        PlanConstant responseCode = PlanConstant.SUCCESS_CREATE_PLAN;
        return new PlanResponse(responseCode.getStatus().value(), responseCode.getMessage(), savedPlan);
    }
}
