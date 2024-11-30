package com.wiyata.wiyata.backend.payload.response.location;

import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CultureResponse implements TypeLocationResponse {

    private Long locationId;
    private String name;
    private Integer areaCode;
    private String address1;
    private String address2;
    private String image;
    private String tel;
    private String summary;
    private String report;
    private LocationType type;

    private boolean parking;
    private String restDate;
    private String fee;
    private String useTime;
    private String spendTime;

    @Override
    public BlockLocationResponse toBlockLocationResponse() {
        return BlockLocationResponse.builder()
                .locationId(locationId)
                .name(name)
                .address1(address1)
                .address2(address2)
                .image(image)
                .type(type)
                .useTime(useTime)
                .restDate(restDate)
                .build();
    }
}
