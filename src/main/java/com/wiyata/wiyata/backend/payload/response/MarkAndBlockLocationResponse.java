package com.wiyata.wiyata.backend.payload.response;

import com.wiyata.wiyata.backend.payload.response.location.BlockLocationResponse;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarkAndBlockLocationResponse {

    private Map<String, List<MarkLocationResponse>> markLocations;
    private Map<String, List<BlockLocationResponse>> blockLocations;
}
