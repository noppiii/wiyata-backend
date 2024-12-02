package com.wiyata.wiyata.backend.service.location.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@Component
public class TypeLocationMapper {

    private final AttractionMapper attractionMapper = Mappers.getMapper(AttractionMapper.class);
    private final CultureMapper cultureMapper = Mappers.getMapper(CultureMapper.class);
    private final FestivalMapper festivalMapper = Mappers.getMapper(FestivalMapper.class);
    private final LeportsMapper leportsMapper = Mappers.getMapper(LeportsMapper.class);
    private final LodgeMapper lodgeMapper = Mappers.getMapper(LodgeMapper.class);
    private final RestaurantMapper restaurantMapper = Mappers.getMapper(RestaurantMapper.class);

}