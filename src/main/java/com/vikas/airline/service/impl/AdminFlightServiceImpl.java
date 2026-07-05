package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.FlightAssignmentRequest;
import com.vikas.airline.dto.request.FlightCreateRequest;
import com.vikas.airline.entity.Aircraft;
import com.vikas.airline.entity.CrewMember;
import com.vikas.airline.entity.Flight;
import com.vikas.airline.entity.Seat;
import com.vikas.airline.enums.SeatClass;
import com.vikas.airline.repository.AircraftRepository;
import com.vikas.airline.repository.CrewMemberRepository;
import com.vikas.airline.repository.FlightRepository;
import com.vikas.airline.repository.SeatRepository;
import com.vikas.airline.service.AdminFlightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFlightServiceImpl implements AdminFlightService {

    private final FlightRepository flightRepo;
    private final SeatRepository seatRepo;
    private final AircraftRepository aircraftRepo;
    private final CrewMemberRepository crewRepo;

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
                    .role(request.getCrewRole())
                    .flight(flight)
                    .build());
        }

        crewRepo.saveAll(crewList);
        flight.setCrewMembers(crewList);

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
                    .booked(false)
                    .build());
        }

        seatRepo.saveAll(seats);
        return "Flight created with " + seats.size() + " seats.";
    }
}
