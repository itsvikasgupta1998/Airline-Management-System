package com.vikas.airline.mapper;

import com.vikas.airline.dto.request.AirportCreateRequest;
import com.vikas.airline.dto.request.AirportUpdateRequest;
import com.vikas.airline.dto.response.AirportResponse;
import com.vikas.airline.entity.Airport;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AirportMapper {

    Airport toEntity(AirportCreateRequest request);

    AirportResponse toResponse(Airport airport);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateAirportFromRequest(
            AirportUpdateRequest request,
            @MappingTarget Airport airport
    );
}