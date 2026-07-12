package com.vikas.airline.mapper;

import com.vikas.airline.dto.response.TicketResponse;
import com.vikas.airline.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "bookingId",
            source = "booking.id")

    @Mapping(target = "bookingReference",
            source = "booking.bookingReference")

    @Mapping(target = "passengerId",
            source = "booking.passenger.id")

    @Mapping(target = "passengerName",
            expression = "java(ticket.getBooking().getPassenger().getFirstName() + \" \" + ticket.getBooking().getPassenger().getLastName())")

    @Mapping(target = "flightId",
            source = "booking.flight.id")

    @Mapping(target = "flightNumber",
            source = "booking.flight.flightNumber")

    @Mapping(target = "sourceAirport",
            source = "booking.flight.sourceAirport.airportName")

    @Mapping(target = "destinationAirport",
            source = "booking.flight.destinationAirport.airportName")
    TicketResponse toResponse(Ticket ticket);

    List<TicketResponse> toResponseList(List<Ticket> tickets);

}