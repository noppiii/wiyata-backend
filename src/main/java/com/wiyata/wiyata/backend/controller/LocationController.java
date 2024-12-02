package com.wiyata.wiyata.backend.controller;

import com.querydsl.core.util.StringUtils;
import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.payload.response.MarkAndBlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.MarkLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.BlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.TypeLocationResponse;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.location.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/location")
public class LocationController {

    private final LocationService locationService;
    private final JwtTokenService jwtTokenService;

    @GetMapping("/{locationId}")
    public ResponseEntity<TypeLocationResponse> viewLocationDetails(@PathVariable("locationId") Long locationId, @RequestParam String locationType) {
        return ResponseEntity.ok(locationService.getLocationDetails(locationId, LocationType.fromValue(StringUtils.capitalize(locationType))));
    }

    @GetMapping("/mark")
    public ResponseEntity<Map<String, List<MarkLocationResponse>>> viewMarkLocationsOnMap() {
        return ResponseEntity.ok().body(locationService.getMarkLocationList());
    }

    @GetMapping("/member")
    public ResponseEntity<MarkAndBlockLocationResponse> viewMemberLocations(HttpServletRequest request) {
        Long memberId = jwtTokenService.tokenToUserId(request);
        return ResponseEntity.ok().body(locationService.getMemberLocationList(memberId));
    }

    @GetMapping("/block")
    public ResponseEntity<Map<String, List<BlockLocationResponse>>> viewBlockLocationList() {
        return ResponseEntity.ok().body(locationService.getBlockLocationList());
    }
}
