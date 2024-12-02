package com.wiyata.wiyata.backend.service.location.mapper;

import com.wiyata.wiyata.backend.config.MapStructMapperConfig;
import com.wiyata.wiyata.backend.entity.location.type.Leports;
import com.wiyata.wiyata.backend.payload.request.location.TypeLocationRequest;
import com.wiyata.wiyata.backend.service.global.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructMapperConfig.class)
public interface LeportsMapper extends GenericMapper<TypeLocationRequest, Leports> {
}