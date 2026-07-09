package com.vikas.airline.mapper;

import com.vikas.airline.dto.request.AircraftCreateRequest;
import com.vikas.airline.dto.request.AircraftUpdateRequest;
import com.vikas.airline.dto.response.AircraftResponse;
import com.vikas.airline.entity.Aircraft;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AircraftMapper {

    Aircraft toEntity(AircraftCreateRequest request);

    AircraftResponse toResponse(Aircraft aircraft);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateAircraftFromRequest(
            AircraftUpdateRequest request,
            @MappingTarget Aircraft aircraft
    );
}