package com.wiyata.wiyata.backend.payload.request.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationWrapperRequest {

    @JsonProperty("location")
    private LocationRequest location;

    @JsonProperty("information")
    private InformationRequest information;

    @JsonProperty("memberLocation")
    private MemberLocationRequest memberLocation;

    @JsonProperty("typeLocation")
    private TypeLocationRequest typeLocation;
}
