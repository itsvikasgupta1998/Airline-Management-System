package com.vikas.airline.service.impl;

import com.vikas.airline.dto.response.BookingSummaryResponse;
import com.vikas.airline.entity.Payment;
import com.vikas.airline.repository.BookingRepository;
import com.vikas.airline.service.BookingSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Override
    public Page<BookingSummaryResponse> getMyBookings(int page, int size, String sortBy, String sortDir) {
        return null;
    }
}
