package com.wiyata.wiyata.backend.payload.response.location;

import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalResponse implements TypeLocationResponse{

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

    private String homepage;
    private String place;
    private String placeInfo;
    private String startDate;
    private String endDate;
    private String playTime;
    private String program;
    private String fee;

    @Override
    public BlockLocationResponse toBlockLocationResponse() {
        return BlockLocationResponse.builder()
                .locationId(locationId)
                .name(name)
                .address1(address1)
                .address2(address2)
                .image(image)
                .type(type)
                .build();
    }
}
