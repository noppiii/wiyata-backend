package com.wiyata.wiyata.backend.payload.request.plan;

import com.wiyata.wiyata.backend.payload.response.MarkLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.BlockLocationResponse;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarkAndBlockLocationListRequest {

    private Map<String, List<MarkLocationResponse>> markLocations;
    private Map<String, List<BlockLocationResponse>> blockLocations;

}
