package com.airline.airlinemanagement.service.impl;

import com.airline.airlinemanagement.dto.request.BookingRequest;
import com.airline.airlinemanagement.dto.response.BookingResponse;
import com.airline.airlinemanagement.entity.*;
import com.airline.airlinemanagement.repository.*;
import com.airline.airlinemanagement.service.BookingService;
import com.airline.airlinemanagement.service.strategy.RefundStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final SeatRepository seatRepo;
    private final FlightRepository flightRepo;
    private final UserRepository userRepo;
    @Autowired
    private RefundStrategy refundStrategy;
    @Autowired private RefundRepository refundRepo;

    @Transactional
    public BookingResponse bookFlight(BookingRequest request, String userEmail) {
        User passenger = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Flight flight = flightRepo.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        List<Seat> seats = seatRepo.findByFlightIdAndSeatNumberIn(flight.getId(), request.getSeatNumbers());

        if (seats.size() != request.getSeatNumbers().size()) {
            throw new RuntimeException("One or more selected seats are invalid");
        }

        for (Seat seat : seats) {
            if (seat.isBooked()) throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());
            seat.setBooked(true);
        }

        double totalFare = seats.stream().mapToDouble(Seat::getPrice).sum();

        Booking booking = Booking.builder()
                .bookingReference(UUID.randomUUID().toString())
                .passenger(passenger)
                .flight(flight)
                .seats(seats)
                .totalFare(totalFare)
                .status(BookingStatus.CONFIRMED)
                .bookingTime(LocalDateTime.now())
                .build();

        Booking saved = bookingRepo.save(booking);
        seats.forEach(seat -> seat.setBooking(saved));

        return new BookingResponse(
                saved.getBookingReference(),
                flight.getFlightNumber(),
                request.getSeatNumbers(),
                totalFare,
                saved.getBookingTime(),
                saved.getStatus()
        );
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId, String userEmail) throws AccessDeniedException {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getPassenger().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Unauthorized cancellation");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED)
            throw new RuntimeException("Booking already cancelled");

        booking.setStatus(BookingStatus.CANCELLED);
        booking.getSeats().forEach(seat -> seat.setBooked(false));
        double refundAmount = refundStrategy.calculateRefund(booking);

        Refund refund = Refund.builder()
                .amount(refundAmount)
                .booking(booking)
                .refundTime(LocalDateTime.now())
                .reason("User-initiated cancellation")
                .build();

        refundRepo.save(refund);
        bookingRepo.save(booking);

        return new BookingResponse(
                booking.getBookingReference(),
                booking.getFlight().getFlightNumber(),
                booking.getSeats().stream().map(Seat::getSeatNumber).toList(),
                refundAmount,
                booking.getBookingTime(),
                booking.getStatus()
        );
    }
}
