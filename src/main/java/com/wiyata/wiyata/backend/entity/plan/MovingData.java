package com.wiyata.wiyata.backend.entity.plan;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovingData {

    private String vehicle;

    private String movingTime;

    private String stayTime;

    private String startTime;

    private String arriveTime;
}