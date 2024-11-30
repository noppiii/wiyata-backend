package com.wiyata.wiyata.backend.payload.response.location;

import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockLocationResponse {

    private Long locationId;
    private String name;
    private String address1;
    private String address2;
    private String image;
    private LocationType type;
    private String useTime;
    private String restDate;
    private String openTime;

}
