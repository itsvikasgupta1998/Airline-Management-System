package com.airline.airlinemanagement.service.impl;

import com.airline.airlinemanagement.dto.response.BookingSummaryResponse;
import com.airline.airlinemanagement.entity.Payment;
import com.airline.airlinemanagement.repository.BookingRepository;
import com.airline.airlinemanagement.service.BookingSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingSummaryServiceImpl implements BookingSummaryService {

    private final BookingRepository bookingRepo;

    @Override
    public List<BookingSummaryResponse> getUserBookings(String userEmail) {
        return bookingRepo.findAll().stream()
                .filter(b -> b.getPassenger().getEmail().equals(userEmail))
                .flatMap(booking -> booking.getSeats().stream().map(seat -> {
                    Payment payment = booking.getPayment(); // If mapped
                    return new BookingSummaryResponse(
                            booking.getBookingReference(),
                            booking.getFlight().getFlightNumber(),
                            seat.getSeatNumber(),
                            booking.getPassenger().getFullName(),
                            payment.getAmount(),
                            payment.getStatus(),
                            booking.getStatus()
                    );
                }))
                .collect(Collectors.toList());
    }
}
