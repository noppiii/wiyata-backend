package com.wiyata.wiyata.backend.service.location;

import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.payload.request.location.InformationRequest;
import com.wiyata.wiyata.backend.payload.request.location.LocationWrapperRequest;
import com.wiyata.wiyata.backend.payload.request.location.MemberLocationRequest;
import com.wiyata.wiyata.backend.payload.request.location.TypeLocationRequest;
import com.wiyata.wiyata.backend.payload.response.MarkAndBlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.MarkLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.BlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.TypeLocationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface LocationService {

    TypeLocationResponse getLocationDetails(Long locationId, LocationType locationType);

    Long createLocationByMember(LocationWrapperRequest wrapperRequest);

    Map<String, List<MarkLocationResponse>> getMarkLocationList();

    Map<String, List<MarkLocationResponse>> getMarkLocationListFromLocationList(List<Location> locationList);

    Map<String, List<BlockLocationResponse>> getBlockLocationList();

    List<MarkLocationResponse> toMarkLocationResponseList(List<Location> locationList);

    Map<String, List<MarkLocationResponse>> classifyMarkLocationResponseList(List<MarkLocationResponse> markLocationResponseList);

    MarkAndBlockLocationResponse getMemberLocationList(Long memberId);

    Map<String, List<BlockLocationResponse>> getBlockLocationListFromLocationList(List<Location> locationList);

    public List<BlockLocationResponse> getBlockLocationsFromTypeLocations(List<TypeLocationResponse> typeLocationResponseList);

    public List<TypeLocationResponse> getTypeLocationResponseListByLocationIds(List<Long> locationIds);

    Map<String, List<BlockLocationResponse>> classifyBlockLocationListWithType(List<BlockLocationResponse> blockLocationResponseList);

    boolean updateLocationInformation(InformationRequest informationRequest, Long locationId);

    boolean updateMemberLocation(MemberLocationRequest memberLocationRequest, Long locationId);

    boolean saveTypeLocation(TypeLocationRequest typeLocationRequest, LocationType locationType) throws NoSuchElementException;
}
