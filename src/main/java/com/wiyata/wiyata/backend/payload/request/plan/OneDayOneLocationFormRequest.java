package com.wiyata.wiyata.backend.payload.request.plan;

import com.wiyata.wiyata.backend.entity.plan.MovingData;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OneDayOneLocationFormRequest {

    private Long locationId;
    private MovingData movingData;
    private String copyLocationId;
    private int days;
}
