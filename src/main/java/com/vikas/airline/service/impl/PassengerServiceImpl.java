package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.PassengerInfoRequest;
import com.vikas.airline.dto.response.PassengerInfoResponse;
import com.vikas.airline.entity.Baggage;
import com.vikas.airline.entity.Passenger;
import com.vikas.airline.entity.Seat;
import com.vikas.airline.repository.BaggageRepository;
import com.vikas.airline.repository.PassengerRepository;
import com.vikas.airline.repository.SeatRepository;
import com.vikas.airline.service.PassengerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final SeatRepository seatRepo;
    private final PassengerRepository passengerRepo;
    private final BaggageRepository baggageRepo;

    @Override
    @Transactional
    public PassengerInfoResponse savePassengerInfo(PassengerInfoRequest req) {
        Seat seat = seatRepo.findById(req.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        Passenger passenger = Passenger.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .contactNumber(req.getContactNumber())
                .dateOfBirth(req.getDateOfBirth())
                .gender(req.getGender())
                .seat(seat)
                .build();

        Passenger savedPassenger = passengerRepo.save(passenger);

        Baggage baggage = Baggage.builder()
                .numberOfBags(req.getNumberOfBags())
                .totalWeight(req.getTotalWeight())
                .specialHandlingRequired(req.isSpecialHandlingRequired())
                .specialNotes(req.getSpecialNotes())
                .passenger(savedPassenger)
                .build();

        baggageRepo.save(baggage);

        return new PassengerInfoResponse(
                seat.getSeatNumber(),
                passenger.getFullName(),
                passenger.getEmail(),
                baggage.getNumberOfBags(),
                baggage.getTotalWeight(),
                baggage.isSpecialHandlingRequired()
        );
    }
}
