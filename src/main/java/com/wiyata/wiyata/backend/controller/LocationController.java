package com.wiyata.wiyata.backend.controller;

import com.querydsl.core.util.StringUtils;
import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.payload.response.location.TypeLocationResponse;
import com.wiyata.wiyata.backend.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{locationId}")
    public ResponseEntity<TypeLocationResponse> viewLocationDetails(@PathVariable("locationId") Long locationId, @RequestParam String locationType) {
        return ResponseEntity.ok(locationService.getLocationDetails(locationId, LocationType.fromValue(StringUtils.capitalize(locationType))));
    }
}
