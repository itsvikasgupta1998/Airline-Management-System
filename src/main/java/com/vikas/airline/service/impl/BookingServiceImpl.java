package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.BookingRequest;
import com.vikas.airline.dto.response.BookingResponse;
import com.vikas.airline.entity.*;
import com.vikas.airline.enums.BookingStatus;
import com.vikas.airline.repository.*;
import com.vikas.airline.service.BookingService;
import com.vikas.airline.service.strategy.RefundStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
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
    private final RefundStrategy refundStrategy;
    private final RefundRepository refundRepo;

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

        BigDecimal totalFare = seats.stream()
                .map(Seat::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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
        BigDecimal refundAmount = refundStrategy.calculateRefund(booking);

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
