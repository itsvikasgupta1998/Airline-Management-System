package com.vikas.airline.service.impl;

import com.vikas.airline.dto.response.PassengerSummaryResponse;
import com.vikas.airline.entity.Baggage;
import com.vikas.airline.entity.Flight;
import com.vikas.airline.entity.Passenger;
import com.vikas.airline.entity.Seat;
import com.vikas.airline.repository.FlightRepository;
import com.vikas.airline.service.AdminPassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPassengerServiceImpl implements AdminPassengerService {

    private final FlightRepository flightRepo;

    @Override
    public Page<PassengerSummaryResponse> getPassengersByFlight(Long flightId, int page, int size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public List<PassengerSummaryResponse> getPassengersByFlight(Long flightId) {
        Flight flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        return null;
    }
}
