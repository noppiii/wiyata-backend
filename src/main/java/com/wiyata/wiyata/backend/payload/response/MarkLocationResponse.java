package com.wiyata.wiyata.backend.payload.response;

import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.entity.location.Coords;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarkLocationResponse {

    private Long locationId;
    private String name;
    private Coords coords;
    private LocationType type;
}
