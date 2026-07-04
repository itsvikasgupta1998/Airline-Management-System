package com.airline.airlinemanagement.service.impl;

import com.airline.airlinemanagement.dto.response.FlightResponse;
import com.airline.airlinemanagement.dto.request.FlightSearchRequest;
import com.airline.airlinemanagement.repository.FlightRepository;
import com.airline.airlinemanagement.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<FlightResponse> searchFlights(FlightSearchRequest request) {
        return flightRepository.findBySourceAndDestinationAndDepartureDate(
                        request.getSource(),
                        request.getDestination(),
                        request.getDepartureDate()
                ).stream()
                .map(f -> new FlightResponse(
                        f.getFlightNumber(),
                        f.getAirline(),
                        f.getSource(),
                        f.getDestination(),
                        f.getDepartureDate(),
                        f.getDepartureTime(),
                        f.getArrivalTime(),
                        f.getFare(),
                        f.getAvailableSeats()
                ))
                .collect(Collectors.toList());
    }
}
