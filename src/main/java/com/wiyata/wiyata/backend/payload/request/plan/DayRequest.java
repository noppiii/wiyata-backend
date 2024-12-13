package com.wiyata.wiyata.backend.payload.request.plan;

import com.wiyata.wiyata.backend.entity.plan.MovingData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DayRequest {

    private Long locationId;
    private String copyLocationId;
    private MovingData movingData;
    private int days;
}
