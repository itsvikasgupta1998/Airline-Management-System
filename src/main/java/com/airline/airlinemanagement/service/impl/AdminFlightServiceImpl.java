package com.airline.airlinemanagement.service.impl;

import com.airline.airlinemanagement.dto.request.FlightAssignmentRequest;
import com.airline.airlinemanagement.dto.request.FlightCreateRequest;
import com.airline.airlinemanagement.entity.*;
import com.airline.airlinemanagement.repository.AircraftRepository;
import com.airline.airlinemanagement.repository.CrewMemberRepository;
import com.airline.airlinemanagement.repository.FlightRepository;
import com.airline.airlinemanagement.repository.SeatRepository;
import com.airline.airlinemanagement.service.AdminFlightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFlightServiceImpl implements AdminFlightService {

    private final FlightRepository flightRepo;
    private final SeatRepository seatRepo;
    @Autowired private AircraftRepository aircraftRepo;
    @Autowired private CrewMemberRepository crewRepo;

    @Override
    @Transactional
    public void assignAircraftAndCrew(FlightAssignmentRequest request) {
        Flight flight = flightRepo.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        Aircraft aircraft = aircraftRepo.findById(request.getAircraftId())
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        flight.setAircraft(aircraft);

        List<CrewMember> crewList = new ArrayList<>();
        for (int i = 0; i < request.getCrewNames().size(); i++) {
            crewList.add(CrewMember.builder()
                    .name(request.getCrewNames().get(i))
                    .role(request.getCrewRoles().get(i))
                    .flight(flight)
                    .build());
        }

        crewRepo.saveAll(crewList);
        flight.setCrew(crewList);

        flightRepo.save(flight);
    }


    @Override
    @Transactional
    public String createFlight(FlightCreateRequest req) {
        Flight flight = Flight.builder()
                .flightNumber(req.getFlightNumber())
                .source(req.getSource())
                .destination(req.getDestination())
                .departureDate(req.getDepartureDate())
                .departureTime(req.getDepartureTime())
                .arrivalTime(req.getArrivalTime())
                .airline(req.getAirline())
                .totalSeats(req.getTotalSeats())
                .availableSeats(req.getTotalSeats())
                .fare(req.getFarePerSeat())
                .build();

        Flight savedFlight = flightRepo.save(flight);

        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= req.getTotalSeats(); i++) {
            seats.add(Seat.builder()
                    .seatNumber("S" + i)
                    .seatClass(SeatClass.ECONOMY)
                    .price(req.getFarePerSeat())
                    .flight(savedFlight)
                    .isBooked(false)
                    .build());
        }

        seatRepo.saveAll(seats);
        return "Flight created with " + seats.size() + " seats.";
    }
}
