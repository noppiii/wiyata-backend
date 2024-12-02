package com.wiyata.wiyata.backend.service.location;

import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.payload.response.MarkAndBlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.MarkLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.BlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.TypeLocationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LocationService {

    TypeLocationResponse getLocationDetails(Long locationId, LocationType locationType);

    Map<String, List<MarkLocationResponse>> getMarkLocationList();

    Map<String, List<MarkLocationResponse>> getMarkLocationListFromLocationList(List<Location> locationList);

    List<MarkLocationResponse> toMarkLocationResponseList(List<Location> locationList);

    Map<String, List<MarkLocationResponse>> classifyMarkLocationResponseList(List<MarkLocationResponse> markLocationResponseList);

    MarkAndBlockLocationResponse getMemberLocationList(Long memberId);

    Map<String, List<BlockLocationResponse>> getBlockLocationListFromLocationList(List<Location> locationList);

    public List<BlockLocationResponse> getBlockLocationsFromTypeLocations(List<TypeLocationResponse> typeLocationResponseList);

    public List<TypeLocationResponse> getTypeLocationResponseListByLocationIds(List<Long> locationIds);

    Map<String, List<BlockLocationResponse>> classifyBlockLocationListWithType(List<BlockLocationResponse> blockLocationResponseList);
}
