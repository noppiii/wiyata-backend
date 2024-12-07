package com.wiyata.wiyata.backend.payload.request.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlanRequest {

    private Long planId;
    private String depart;
    private String name;
    private int periods;
}
