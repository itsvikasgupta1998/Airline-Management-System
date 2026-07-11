package com.vikas.airline.mapper;

import com.vikas.airline.dto.response.BookingResponse;
import com.vikas.airline.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "flightId", source = "flight.id")
    @Mapping(target = "passengerId", source = "passenger.id")
    @Mapping(target = "seatId", source = "seat.id")
    BookingResponse toResponse(Booking booking);

}