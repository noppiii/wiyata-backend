package com.wiyata.wiyata.backend.payload.response;

import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.payload.response.location.BlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.TypeLocationResponse;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantResponse implements TypeLocationResponse {

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

    private String popularMenu;
    private String openTime;
    private boolean packing;
    private boolean parking;
    private String restDate;
    private String menu;

    @Override
    public BlockLocationResponse toBlockLocationResponse() {
        return BlockLocationResponse.builder()
                .locationId(locationId)
                .name(name)
                .address1(address1)
                .address2(address2)
                .image(image)
                .openTime(openTime)
                .type(type)
                .build();
    }
}
