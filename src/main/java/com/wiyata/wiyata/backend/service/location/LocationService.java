package com.wiyata.wiyata.backend.service.location;

import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.payload.response.location.TypeLocationResponse;
import org.springframework.http.ResponseEntity;

public interface LocationService {

    TypeLocationResponse getLocationDetails(Long locationId, LocationType locationType);
}
