package com.airline.airlinemanagement.service.impl;

import com.airline.airlinemanagement.dto.response.PassengerSummaryResponse;
import com.airline.airlinemanagement.entity.Baggage;
import com.airline.airlinemanagement.entity.Flight;
import com.airline.airlinemanagement.entity.Passenger;
import com.airline.airlinemanagement.entity.Seat;
import com.airline.airlinemanagement.repository.FlightRepository;
import com.airline.airlinemanagement.service.AdminPassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPassengerServiceImpl implements AdminPassengerService {

    private final FlightRepository flightRepo;

    @Override
    public List<PassengerSummaryResponse> getPassengersByFlight(Long flightId) {
        Flight flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        return flight.getSeats().stream()
                .filter(Seat::isBooked)
                .map(seat -> {
                    Passenger p = seat.getPassenger();
                    Baggage b = p.getBaggage();
                    return new PassengerSummaryResponse(
                            p.getFullName(),
                            p.getEmail(),
                            p.getContactNumber(),
                            seat.getSeatNumber(),
                            b.getNumberOfBags(),
                            b.getTotalWeight()
                    );
                }).toList();
    }
}
