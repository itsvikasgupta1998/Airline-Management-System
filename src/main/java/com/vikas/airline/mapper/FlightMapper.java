package com.vikas.airline.mapper;

import com.vikas.airline.dto.request.FlightCreateRequest;
import com.vikas.airline.dto.request.FlightUpdateRequest;
import com.vikas.airline.dto.response.FlightResponse;
import com.vikas.airline.entity.Flight;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(target = "availableSeats", source = "totalSeats")
    @Mapping(target = "fare", source = "farePerSeat")
    @Mapping(target = "sourceAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)
    @Mapping(target = "aircraft", ignore = true)
    @Mapping(target = "crewMembers", ignore = true)
    @Mapping(target = "seats", ignore = true)

    Flight toEntity(FlightCreateRequest request);


    @Mapping(target = "sourceAirportId", source = "sourceAirport.id")
    @Mapping(target = "sourceAirportCode", source = "sourceAirport.iataCode")
    @Mapping(target = "sourceAirportName", source = "sourceAirport.airportName")
    @Mapping(target = "sourceCity", source = "sourceAirport.city")

    @Mapping(target = "destinationAirportId", source = "destinationAirport.id")
    @Mapping(target = "destinationAirportCode", source = "destinationAirport.iataCode")
    @Mapping(target = "destinationAirportName", source = "destinationAirport.airportName")
    @Mapping(target = "destinationCity", source = "destinationAirport.city")
    FlightResponse toResponse(Flight flight);


    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )

    @Mapping(target = "flightNumber", ignore = true)
    @Mapping(target = "availableSeats", ignore = true)

    @Mapping(target = "sourceAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)

    @Mapping(target = "aircraft", ignore = true)
    @Mapping(target = "crewMembers", ignore = true)
    @Mapping(target = "seats", ignore = true)

    @Mapping(target = "fare", source = "fare")

    void updateFlightFromRequest(
            FlightUpdateRequest request,
            @MappingTarget Flight flight
    );

}
