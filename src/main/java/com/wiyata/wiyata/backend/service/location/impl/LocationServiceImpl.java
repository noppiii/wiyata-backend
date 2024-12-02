package com.wiyata.wiyata.backend.service.location.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.entity.enumerated.LocationType;
import com.wiyata.wiyata.backend.entity.location.Location;
import com.wiyata.wiyata.backend.entity.location.type.*;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.request.location.InformationRequest;
import com.wiyata.wiyata.backend.payload.request.location.LocationWrapperRequest;
import com.wiyata.wiyata.backend.payload.request.location.MemberLocationRequest;
import com.wiyata.wiyata.backend.payload.request.location.TypeLocationRequest;
import com.wiyata.wiyata.backend.payload.response.MarkAndBlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.MarkLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.BlockLocationResponse;
import com.wiyata.wiyata.backend.payload.response.location.TypeLocationResponse;
import com.wiyata.wiyata.backend.repository.InformationRepository;
import com.wiyata.wiyata.backend.repository.LocationRepository;
import com.wiyata.wiyata.backend.repository.MemberLocationRepository;
import com.wiyata.wiyata.backend.service.location.LocationService;
import com.wiyata.wiyata.backend.service.location.mapper.TypeLocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final InformationRepository informationRepository;
    private final MemberLocationRepository memberLocationRepository;
    private final TypeLocationMapper typeLocationMapper;

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

    @Override
    public Long createLocationByMember(LocationWrapperRequest wrapperRequest) {
        Location location = locationRepository.save(wrapperRequest.getLocation().toEntity());
        Long locationId = location.getId();
        LocationType type = location.getType();

        TypeLocationRequest typeLocationRequestDto = wrapperRequest.getTypeLocation();
        typeLocationRequestDto.setLocationId(locationId);

        updateLocationInformation(wrapperRequest.getInformation(), locationId);
        updateMemberLocation(wrapperRequest.getMemberLocation(), locationId);
        saveTypeLocation(typeLocationRequestDto, type);

        return locationId;
    }

    @Override
    public Map<String, List<MarkLocationResponse>> getMarkLocationList() {
        List<Location> locationList = locationRepository.findAllByIsMemberFalse();
        return getMarkLocationListFromLocationList(locationList);
    }

    @Override
    public Map<String, List<MarkLocationResponse>> getMarkLocationListFromLocationList(List<Location> locationList) {
        List<MarkLocationResponse> markLocationResponseList = toMarkLocationResponseList(locationList);
        return classifyMarkLocationResponseList(markLocationResponseList);
    }

    @Override
    public Map<String, List<BlockLocationResponse>> getBlockLocationList() {
        List<Location> locationList = locationRepository.findAllByIsMemberFalse();
        return getBlockLocationListFromLocationList(locationList);
    }

    @Override
    public List<MarkLocationResponse> toMarkLocationResponseList(List<Location> locationList) {
        return locationList.stream().map(Location::toMarkLocationResponse).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<MarkLocationResponse>> classifyMarkLocationResponseList(List<MarkLocationResponse> markLocationResponseList) {
        return markLocationResponseList.stream().collect(Collectors
                .groupingBy(markLocationResponse -> markLocationResponse.getType().getType()));
    }

    @Override
    public MarkAndBlockLocationResponse getMemberLocationList(Long memberId) {
        List<Location> locations = locationRepository.findLocationsByMemberId(memberId);
        return new MarkAndBlockLocationResponse(getMarkLocationListFromLocationList(locations),
                getBlockLocationListFromLocationList(locations));
    }

    @Override
    public Map<String, List<BlockLocationResponse>> getBlockLocationListFromLocationList(List<Location> locationList) {
        List<Long> locationIds = locationList.stream().map(Location::getId).collect(Collectors.toList());
        List<BlockLocationResponse> blockLocationResponsesList = getBlockLocationsFromTypeLocations(getTypeLocationResponseListByLocationIds(locationIds));
        return classifyBlockLocationListWithType(blockLocationResponsesList);
    }

    @Override
    public List<BlockLocationResponse> getBlockLocationsFromTypeLocations(List<TypeLocationResponse> typeLocationResponseList) {
        return typeLocationResponseList.stream().map(TypeLocationResponse::toBlockLocationResponse).collect(Collectors.toList());
    }

    @Override
    public List<TypeLocationResponse> getTypeLocationResponseListByLocationIds(List<Long> locationIds) {
        List<TypeLocationResponse> typeLocationResponseList = new ArrayList<>();
        typeLocationResponseList.addAll(locationRepository.findAttractionsByLocationIds(locationIds));
        typeLocationResponseList.addAll(locationRepository.findCulturesByLocationIds(locationIds));
        typeLocationResponseList.addAll(locationRepository.findFestivalsByLocationIds(locationIds));
        typeLocationResponseList.addAll(locationRepository.findLeportsByLocationIds(locationIds));
        typeLocationResponseList.addAll(locationRepository.findLodgesByLocationIds(locationIds));
        typeLocationResponseList.addAll(locationRepository.findRestaurantsByLocationIds(locationIds));

        return typeLocationResponseList;
    }

    @Override
    public Map<String, List<BlockLocationResponse>> classifyBlockLocationListWithType(List<BlockLocationResponse> blockLocationResponseList) {
        return blockLocationResponseList.stream().collect(Collectors
                .groupingBy(blockLocationResponse -> blockLocationResponse.getType().getType()));
    }

    @Override
    @Transactional
    public boolean updateLocationInformation(InformationRequest informationRequest, Long locationId) {
        return informationRepository.save(informationRequest.toEntity(locationId)).getLocationId().equals(locationId);
    }

    @Override
    @Transactional
    public boolean updateMemberLocation(MemberLocationRequest memberLocationRequest, Long locationId) {
        return memberLocationRepository.save(memberLocationRequest.toEntity(locationId)).getLocationId().equals(locationId);
    }

    @Override
    public boolean saveTypeLocation(TypeLocationRequest typeLocationRequest, LocationType locationType) throws NoSuchElementException {
        switch (locationType) {
            case ATTRACTION:
                Attraction attraction = typeLocationMapper.getAttractionMapper().toEntity(typeLocationRequest);
                locationRepository.saveAttraction(attraction);
                return true;
            case CULTURE:
                Culture culture = typeLocationMapper.getCultureMapper().toEntity(typeLocationRequest);
                locationRepository.saveCulture(culture);
                return true;
            case FESTIVAL:
                Festival festival = typeLocationMapper.getFestivalMapper().toEntity(typeLocationRequest);
                locationRepository.saveFestival(festival);
                return true;
            case LEPORTS:
                Leports leports = typeLocationMapper.getLeportsMapper().toEntity(typeLocationRequest);
                locationRepository.saveLeports(leports);
                return true;
            case LODGE:
                Lodge lodge = typeLocationMapper.getLodgeMapper().toEntity(typeLocationRequest);
                locationRepository.saveLodge(lodge);
                return true;
            case RESTAURANT:
                Restaurant restaurant = typeLocationMapper.getRestaurantMapper().toEntity(typeLocationRequest);
                locationRepository.saveRestaurant(restaurant);
                return true;
        }
        throw new NoSuchElementException("Jenis ini tidak dapat digunakan");
    }
}
