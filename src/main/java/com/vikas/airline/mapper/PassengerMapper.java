package com.vikas.airline.mapper;

import com.vikas.airline.dto.request.CreatePassengerRequest;
import com.vikas.airline.dto.request.UpdatePassengerRequest;
import com.vikas.airline.dto.response.PassengerResponse;
import com.vikas.airline.entity.Passenger;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    Passenger toEntity(CreatePassengerRequest request);

    PassengerResponse toResponse(Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updatePassengerFromRequest(UpdatePassengerRequest request,
                                    @MappingTarget Passenger passenger);
}