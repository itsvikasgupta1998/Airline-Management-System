package com.vikas.airline.service.impl;

import com.vikas.airline.dto.response.SeatResponse;
import com.vikas.airline.dto.response.SeatSummaryResponse;
import com.vikas.airline.entity.Flight;
import com.vikas.airline.entity.Seat;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.SeatMapper;
import com.vikas.airline.repository.FlightRepository;
import com.vikas.airline.repository.SeatRepository;
import com.vikas.airline.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final SeatMapper seatMapper;

    @Override
    public List<SeatResponse> getAllSeats(Long flightId) {

        List<Seat> seats = seatRepository.findByFlightId(flightId);

        log.info("Fetched {} seats for FlightId={}", seats.size(), flightId);

        return seatMapper.toResponseList(seats);
    }

    @Override
    public List<SeatResponse> getAvailableSeats(Long flightId) {

        List<Seat> seats = seatRepository.findByFlightIdAndBookedFalse(flightId);

        log.info("Fetched available seats for FlightId={}", flightId);

        return seatMapper.toResponseList(seats);
    }

    @Override
    public List<SeatResponse> getBookedSeats(Long flightId) {

        List<Seat> seats = seatRepository.findByFlightIdAndBookedTrue(flightId)
                .stream()
                .filter(Seat::getBooked)
                .toList();

        log.info("Fetched booked seats for FlightId={}", flightId);

        return seatMapper.toResponseList(seats);
    }

    @Override
    public SeatResponse getSeatBySeatNumber(Long flightId, String seatNumber) {

        Seat seat = seatRepository
                .findByFlightIdAndSeatNumber(flightId, seatNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat " + seatNumber + " not found for Flight " + flightId
                        ));

        log.info("Fetched Seat {} for FlightId={}", seatNumber, flightId);

        return seatMapper.toResponse(seat);
    }

    @Override
    public SeatSummaryResponse getSeatSummary(Long flightId) {

        Flight flight = flightRepository.findByIdAndActiveTrue(flightId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Flight", flightId));

        long booked = seatRepository.countByFlightIdAndBookedTrue(flightId);

        long available = seatRepository.countByFlightIdAndBookedFalse(flightId);

        return SeatSummaryResponse.builder()
                .flightId(flightId)
                .totalSeats(flight.getTotalSeats())
                .bookedSeats(booked)
                .availableSeats(available)
                .build();
    }
}