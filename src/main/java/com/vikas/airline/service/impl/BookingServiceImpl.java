package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.BookingSearchRequest;
import com.vikas.airline.dto.request.CancelBookingRequest;
import com.vikas.airline.dto.request.CreateBookingRequest;
import com.vikas.airline.dto.request.UpdateBookingRequest;
import com.vikas.airline.dto.response.BookingResponse;
import com.vikas.airline.entity.*;
import com.vikas.airline.enums.BookingStatus;
import com.vikas.airline.enums.PaymentStatus;
import com.vikas.airline.enums.TravelClass;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.BookingMapper;
import com.vikas.airline.repository.*;
import com.vikas.airline.service.BookingService;
import com.vikas.airline.specification.BookingSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public BookingResponse createBooking(
            CreateBookingRequest request) {

        Flight flight = getActiveFlight(request.getFlightId());

        Passenger passenger = getActivePassenger(request.getPassengerId());

        Seat seat = getActiveSeat(request.getSeatId());

        User user = getUser(request.getUserId());

        validateSeatBelongsToFlight(flight, seat);

        validatePassport(passenger);

        validateSeatAvailability(seat);

        validateTravelClass(seat, request);

        validatePassengerAlreadyBooked(passenger, flight);

        validateFlightDeparture(flight);

        Booking booking = buildBooking(
                request,
                flight,
                passenger,
                seat,
                user);

        reserveSeat(seat);

        updateFlightAvailability(flight);

        Booking savedBooking = saveBooking(booking);

        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponse updateBooking(
            Long id,
            UpdateBookingRequest request) {

        Booking booking = getActiveBooking(id);

        validateBookingEditable(booking);

        if (request.getSeatId() != null &&
                !request.getSeatId().equals(
                        booking.getSeat().getId())) {

            Seat newSeat = getActiveSeat(request.getSeatId());

            validateSeatBelongsToFlight(
                    booking.getFlight(),
                    newSeat);

            validateSeatAvailability(newSeat);

            TravelClass travelClass =
                    request.getTravelClass() != null
                            ? request.getTravelClass()
                            : booking.getTravelClass();

            validateTravelClass(
                    newSeat,
                    travelClass);

            releaseSeat(booking.getSeat());

            reserveSeat(newSeat);

            booking.setSeat(newSeat);

            booking.setTravelClass(travelClass);

            recalculateFare(
                    booking,
                    newSeat);
        }

        else if (request.getTravelClass() != null) {

            validateTravelClass(
                    booking.getSeat(),
                    request.getTravelClass());

            booking.setTravelClass(
                    request.getTravelClass());
        }

        if (request.getRemarks() != null) {

            booking.setRemarks(normalizeRemarks(request.getRemarks().trim()));
        }

        Booking updatedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponse(updatedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) {

        Booking booking = getActiveBooking(id);

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingResponse> getAllBookings(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        validatePagination(page, size);
        validateSortField(sortBy);
        Pageable pageable = buildPageable(
                page,
                size,
                sortBy,
                sortDir);

        return bookingRepository
                .findAllByActiveTrue(pageable)
                .map(bookingMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {

        Booking booking = getBooking(id);
        validateBookingNotAlreadyDeleted(booking);
        releaseSeat(booking.getSeat());
        incrementFlightAvailability(booking.getFlight());
        validateBookingNotCancelled(booking);
        booking.setActive(false);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void restoreBooking(Long id) {

        Booking booking = getBooking(id);
        validateBookingNotAlreadyActive(booking);
        validateSeatAvailability(booking.getSeat());
        validateFlightAvailability(booking.getFlight());
        validateBookingNotCancelled(booking);
        reserveSeat(booking.getSeat());
        decrementFlightAvailability(booking.getFlight());
        booking.setActive(true);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingResponse> searchBookings(
            BookingSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        validatePagination(page, size);

        validateSortField(sortBy);

        Pageable pageable = buildPageable(
                page,
                size,
                sortBy,
                sortDir);


        return bookingRepository.findAll(

                        BookingSpecification.search(request),

                        pageable)

                .map(bookingMapper::toResponse);
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(
            Long id,
            CancelBookingRequest request) {

        Booking booking = getActiveBooking(id);

        validateBookingNotCancelled(booking);

        validateFlightDepartureForCancellation(
                booking.getFlight());

        BigDecimal refund =
                calculateRefund(booking);

        releaseSeat(booking.getSeat());

        incrementFlightAvailability(
                booking.getFlight());

        booking.setBookingStatus(
                BookingStatus.CANCELLED);

        booking.setCancelledAt(
                LocalDateTime.now());

        booking.setCancellationReason(
                request.getCancellationReason());

        booking.setRefundAmount(refund);

        booking.setRefundProcessedAt(
                LocalDateTime.now());

        booking.setPaymentStatus(
                resolvePaymentStatus(refund));

        Booking saved =
                bookingRepository.save(booking);

        return bookingMapper.toResponse(saved);
    }



                        /**   HELPER METHODS  **/

    private Booking getBooking(Long id) {

        return bookingRepository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Booking not found with id: " + id
                        )
                );
    }

    private Booking getActiveBooking(Long id) {

        return bookingRepository.findByIdAndActiveTrue(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Booking not found with id: " + id
                        )
                );
    }

    private Flight getActiveFlight(Long id) {

        return flightRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Flight not found with id: " + id));
    }

    private Passenger getActivePassenger(Long id) {

        return passengerRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Passenger not found with id: " + id));
    }

    private Seat getActiveSeat(Long id) {

        return seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat not found with id: " + id));
    }

    private User getUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));
    }

    private void validateSeatBelongsToFlight(
            Flight flight,
            Seat seat) {

        if (!seat.getFlight().getId().equals(flight.getId())) {

            throw new BadRequestException(
                    "Selected seat does not belong to the selected flight.");
        }
    }

    private void validatePassport(Passenger passenger) {

        if (passenger.getPassportExpiry().isBefore(LocalDate.now())) {

            throw new BadRequestException(
                    "Passenger passport has expired.");
        }
    }

    private void validateFlightDeparture(Flight flight) {

        LocalDateTime departureDateTime = LocalDateTime.of(
                flight.getDepartureDate(),
                flight.getDepartureTime()
        );

        if (departureDateTime.isBefore(LocalDateTime.now())) {

            throw new BadRequestException(
                    "Booking cannot be created because the flight has already departed.");
        }
    }

    private void validateSeatAvailability(Seat seat) {

        if (Boolean.TRUE.equals(seat.getBooked())) {
            throw new BadRequestException(
                    "Selected seat is already booked.");
        }
    }

    private void validateTravelClass(
            Seat seat,
            CreateBookingRequest request) {

        if (seat.getTravelClass() != request.getTravelClass()) {

            throw new BadRequestException(
                    "Selected seat does not match the requested travel class.");
        }
    }

    private Booking buildBooking(
            CreateBookingRequest request,
            Flight flight,
            Passenger passenger,
            Seat seat,
            User user) {

        BigDecimal baseFare = seat.getPrice();
        BigDecimal tax = baseFare.multiply(new BigDecimal("0.18"));
        BigDecimal serviceFee = new BigDecimal("250");
        BigDecimal discount = BigDecimal.ZERO;

        BigDecimal totalFare = baseFare
                .add(tax)
                .add(serviceFee)
                .subtract(discount);

        return Booking.builder()
                .bookingReference(generateBookingReference())
                .flight(flight)
                .passenger(passenger)
                .seat(seat)
                .user(user)
                .travelClass(request.getTravelClass())
                .bookingStatus(BookingStatus.CONFIRMED)
                .paymentStatus(PaymentStatus.PENDING)
                .bookingDate(LocalDateTime.now())
                .baseFare(baseFare)
                .tax(tax)
                .serviceFee(serviceFee)
                .discount(discount)
                .totalFare(totalFare)
                .remarks(request.getRemarks())
                .currency("INR")
                .active(true)
                .build();
    }

    private String generateBookingReference() {

        return "BK" +
                System.currentTimeMillis();
    }

    private void reserveSeat(Seat seat) {

        seat.setBooked(true);

        seatRepository.save(seat);
    }

    private void updateFlightAvailability(Flight flight) {

        flight.setAvailableSeats(
                flight.getAvailableSeats() - 1
        );

        flightRepository.save(flight);
    }

    private Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    private void validatePassengerAlreadyBooked(
            Passenger passenger,
            Flight flight) {

        if (bookingRepository.existsByPassengerIdAndFlightIdAndActiveTrue(
                passenger.getId(),
                flight.getId())) {

            throw new BadRequestException(
                    "Passenger already has an active booking for this flight.");
        }
    }

    private void validateBookingEditable(
            Booking booking) {

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Cancelled booking cannot be updated.");
        }
    }

    private void validateTravelClass(
            Seat seat,
            TravelClass travelClass) {

        if (seat.getTravelClass() != travelClass) {

            throw new BadRequestException(
                    "Selected seat does not match the requested travel class.");
        }
    }

    private void releaseSeat(
            Seat seat) {

        seat.setBooked(false);

        seatRepository.save(seat);
    }

    private void recalculateFare(
            Booking booking,
            Seat seat) {

        BigDecimal baseFare = seat.getPrice();

        BigDecimal tax =
                baseFare.multiply(new BigDecimal("0.18"));

        BigDecimal serviceFee =
                booking.getServiceFee();

        BigDecimal discount =
                booking.getDiscount();

        BigDecimal totalFare =
                baseFare
                        .add(tax)
                        .add(serviceFee)
                        .subtract(discount);

        booking.setBaseFare(baseFare);
        booking.setTax(tax);
        booking.setTotalFare(totalFare);
    }

    private String normalizeRemarks(
            String remarks) {

        if (remarks == null ||
                remarks.isBlank()) {

            return null;
        }

        return remarks.trim();
    }

    private Pageable buildPageable(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(
                page,
                size,
                sort);
    }

    private void validatePagination(
            int page,
            int size) {

        if (page < 0) {

            throw new BadRequestException(
                    "Page number cannot be negative.");
        }

        if (size <= 0) {

            throw new BadRequestException(
                    "Page size must be greater than zero.");
        }

        if (size > 100) {

            throw new BadRequestException(
                    "Page size cannot exceed 100.");
        }
    }

    private void validateSortField(
            String sortBy) {

        List<String> allowedFields = List.of(

                "id",

                "bookingDate",

                "bookingReference",

                "bookingStatus",

                "paymentStatus",

                "totalFare"
        );

        if (!allowedFields.contains(sortBy)) {

            throw new BadRequestException(

                    "Invalid sort field: " + sortBy);
        }
    }

    private void validateBookingNotAlreadyDeleted(
            Booking booking) {

        if (!Boolean.TRUE.equals(booking.getActive())) {

            throw new BadRequestException(
                    "Booking is already deleted.");
        }
    }

    private void validateBookingNotAlreadyActive(
            Booking booking) {

        if (Boolean.TRUE.equals(booking.getActive())) {

            throw new BadRequestException(
                    "Booking is already active.");
        }
    }

    private void incrementFlightAvailability(
            Flight flight) {

        flight.setAvailableSeats(
                flight.getAvailableSeats() + 1);

        flightRepository.save(flight);
    }

    private void decrementFlightAvailability(
            Flight flight) {

        flight.setAvailableSeats(
                flight.getAvailableSeats() - 1);

        flightRepository.save(flight);
    }

    private void validateFlightAvailability(
            Flight flight) {

        if (flight.getAvailableSeats() <= 0) {

            throw new BadRequestException(
                    "No seats available for this flight.");
        }
    }

    private void validateBookingNotCancelled(
            Booking booking) {

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {

            throw new BadRequestException(
                    "Cancelled booking cannot be deleted or restored.");
        }
    }

    private void validateFlightDepartureForCancellation(
            Flight flight){

        LocalDateTime departure = LocalDateTime.of(

                flight.getDepartureDate(),

                flight.getDepartureTime());

        if(LocalDateTime.now().isAfter(departure)){

            throw new BadRequestException(

                    "Cannot cancel booking after flight departure.");
        }
    }

    private BigDecimal calculateRefund(
            Booking booking){

        LocalDateTime departure = LocalDateTime.of(

                booking.getFlight().getDepartureDate(),

                booking.getFlight().getDepartureTime());

        long hours = Duration.between(
                        LocalDateTime.now(),
                        departure).toHours();

        BigDecimal fare = booking.getTotalFare();

        if(hours>=72){

            return fare;
        }

        if(hours>=24){

            return fare.multiply(
                    new BigDecimal("0.80"));
        }

        if(hours>=6){

            return fare.multiply(
                    new BigDecimal("0.50"));
        }

        return BigDecimal.ZERO;
    }

    private PaymentStatus resolvePaymentStatus(
            BigDecimal refund){

        if(refund.compareTo(BigDecimal.ZERO)==0){

            return PaymentStatus.NON_REFUNDABLE;
        }

        return PaymentStatus.REFUNDED;
    }

}
