package com.vikas.airline.service.impl;

import com.vikas.airline.dto.response.PassengerSummaryResponse;
import com.vikas.airline.entity.Baggage;
import com.vikas.airline.entity.Flight;
import com.vikas.airline.entity.Passenger;
import com.vikas.airline.entity.Seat;
import com.vikas.airline.repository.FlightRepository;
import com.vikas.airline.service.AdminPassengerService;
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
