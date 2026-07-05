package com.vikas.airline.service.impl;

import com.vikas.airline.dto.response.FlightResponse;
import com.vikas.airline.dto.request.FlightSearchRequest;
import com.vikas.airline.repository.FlightRepository;
import com.vikas.airline.service.FlightService;
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
