package com.wiyata.wiyata.backend.service.location.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.response.location.TypeLocationResponse;
import com.wiyata.wiyata.backend.repository.LocationRepository;
import com.wiyata.wiyata.backend.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public TypeLocationResponse getLocationDetails(Long locationId, LocationType locationType) throws NoSuchElementException {
        switch (locationType) {
            case ATTRACTION:
                return locationRepository.findAttractionByLocationId(locationId);
            case CULTURE:
                return locationRepository.findCultureByLocationId(locationId);
            case FESTIVAL:
                return locationRepository.findFestivalByLocationId(locationId);
            case LEPORTS:
                return locationRepository.findLeportByLocationId(locationId);
            case LODGE:
                return locationRepository.findLodgeByLocationId(locationId);
            case RESTAURANT:
                return locationRepository.findRestaurantByLocationId(locationId);
        }
        throw new NoSuchElementException("Lokasi tersebut tidak ditemukan");
    }
}
