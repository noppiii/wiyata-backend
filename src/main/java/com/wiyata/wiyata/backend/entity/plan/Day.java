package com.wiyata.wiyata.backend.entity.plan;

import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.payload.request.plan.DayRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Embedded
    private MovingData movingData;

    private Integer days;

    private String copyLocationId;

    public DayRequest toRequest() {
        return DayRequest.builder()
                .locationId(location.getId())
                .copyLocationId(copyLocationId)
                .movingData(movingData)
                .days(days)
                .build();
    }
}
