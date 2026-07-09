package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.FlightAssignmentRequest;
import com.vikas.airline.dto.request.FlightCreateRequest;
import com.vikas.airline.dto.request.FlightUpdateRequest;
import com.vikas.airline.dto.response.FlightResponse;
import com.vikas.airline.entity.*;
import com.vikas.airline.enums.SeatClass;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.FlightMapper;
import com.vikas.airline.repository.*;
import com.vikas.airline.service.AdminFlightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminFlightServiceImpl implements AdminFlightService {

    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final SeatRepository seatRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightResponse createFlight(
            FlightCreateRequest request
    ) {

        if (flightRepository.existsByFlightNumberAndDepartureDateAndActiveTrue(
                request.getFlightNumber(),
                request.getDepartureDate())) {

            throw new BadRequestException(
                    "Flight already exists for the selected departure date."
            );
        }

        Aircraft aircraft = aircraftRepository
                .findByIdAndActiveTrue(request.getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aircraft",
                                request.getAircraftId()
                        ));

        Flight flight = flightMapper.toEntity(request);

        flight.setAircraft(aircraft);

        Airport sourceAirport = airportRepository
                .findByIdAndActiveTrue(request.getSourceAirportId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Source Airport",
                                request.getSourceAirportId()));

        Airport destinationAirport = airportRepository
                .findByIdAndActiveTrue(request.getDestinationAirportId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Destination Airport",
                                request.getDestinationAirportId()));

        flight.setSourceAirport(sourceAirport);
        flight.setDestinationAirport(destinationAirport);

        flight.setAvailableSeats(request.getTotalSeats());

        Flight savedFlight = flightRepository.save(flight);

        createSeats(savedFlight);

        log.info(
                "Flight created successfully. FlightId={}, FlightNumber={}",
                savedFlight.getId(),
                savedFlight.getFlightNumber()
        );

        return flightMapper.toResponse(savedFlight);
    }

    @Override
    public FlightResponse updateFlight(
            Long flightId,
            FlightUpdateRequest request
    ) {

        Flight flight = findFlightById(flightId);

        if(request.getAircraftId()!=null){

            Aircraft aircraft = aircraftRepository
                    .findByIdAndActiveTrue(request.getAircraftId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Aircraft",
                                    request.getAircraftId()
                            ));

            flight.setAircraft(aircraft);
        }

        flightMapper.updateFlightFromRequest(request, flight);

        if (request.getSourceAirportId() != null) {

            Airport airport = airportRepository
                    .findByIdAndActiveTrue(request.getSourceAirportId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Source Airport",
                                    request.getSourceAirportId()));

            flight.setSourceAirport(airport);
        }

        if (request.getDestinationAirportId() != null) {

            Airport airport = airportRepository
                    .findByIdAndActiveTrue(request.getDestinationAirportId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Destination Airport",
                                    request.getDestinationAirportId()));

            flight.setDestinationAirport(airport);
        }


        Flight updatedFlight = flightRepository.save(flight);

        log.info(
                "Flight updated successfully. FlightId={}, FlightNumber={}",
                updatedFlight.getId(),
                updatedFlight.getFlightNumber()
        );

        return flightMapper.toResponse(updatedFlight);
    }

    @Override
    @Transactional
    public void deleteFlight(Long flightId) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Flight", flightId));

        if (!Boolean.TRUE.equals(flight.getActive())) {
            throw new BadRequestException("Flight is already deleted.");
        }

        flight.setActive(false);

        flightRepository.save(flight);

        log.info(
                "Flight deleted successfully. FlightId={}",
                flightId
        );
    }

    @Override
    public void assignAircraftAndCrew(
            FlightAssignmentRequest request
    ) {

        Flight flight = findFlightById(request.getFlightId());

        Aircraft aircraft = aircraftRepository.findById(request.getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aircraft",
                                request.getAircraftId()
                        ));

        flight.setAircraft(aircraft);

        crewMemberRepository.deleteAll(flight.getCrewMembers());

        List<CrewMember> crewMembers =
                request.getCrewNames()
                        .stream()
                        .map(name ->
                                CrewMember.builder()
                                        .name(name)
                                        .role(request.getCrewRole())
                                        .flight(flight)
                                        .build()
                        )
                        .toList();

        crewMemberRepository.saveAll(crewMembers);

        flight.setCrewMembers(crewMembers);

        flightRepository.save(flight);

        log.info(
                "Aircraft {} assigned to Flight {}",
                aircraft.getId(),
                flight.getFlightNumber()
        );
    }

    private Flight findFlightById(Long id) {

        return flightRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Flight", id));
    }

    private void createSeats(Flight flight) {

        List<Seat> seats = IntStream.rangeClosed(1, flight.getTotalSeats())
                .mapToObj(i ->
                        Seat.builder()
                                .flight(flight)
                                .seatNumber("S" + i)
                                .seatClass(SeatClass.ECONOMY)
                                .price(flight.getFare())
                                .booked(false)
                                .build())
                .toList();

        seatRepository.saveAll(seats);
    }

    @Override
    public void restoreFlight(Long flightId) {
        log.info("Restoring flight : {}", flightId);

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Flight not found with id : " + flightId
                        ));

        if (Boolean.TRUE.equals(flight.getActive())) {
            throw new BadRequestException("Flight is already active.");
        }

        flight.setActive(true);

        flightRepository.save(flight);

        log.info("Flight restored successfully : {}", flightId);

    }

}
