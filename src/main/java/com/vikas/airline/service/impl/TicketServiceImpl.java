package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.TicketSearchRequest;
import com.vikas.airline.dto.request.UpdateTicketRequest;
import com.vikas.airline.dto.response.TicketResponse;
import com.vikas.airline.entity.Booking;
import com.vikas.airline.entity.Payment;
import com.vikas.airline.entity.Ticket;
import com.vikas.airline.enums.BookingStatus;
import com.vikas.airline.enums.PaymentStatus;
import com.vikas.airline.enums.TicketStatus;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.TicketMapper;
import com.vikas.airline.repository.BookingRepository;
import com.vikas.airline.repository.PaymentRepository;
import com.vikas.airline.repository.TicketRepository;
import com.vikas.airline.service.EmailService;
import com.vikas.airline.service.TicketService;
import com.vikas.airline.specification.TicketSpecification;
import com.vikas.airline.utils.TicketHtmlBuilder;
import com.vikas.airline.utils.TicketPdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final TicketMapper ticketMapper;
    private final TicketPdfGenerator ticketPdfGenerator;
    private final TicketHtmlBuilder ticketHtmlBuilder;
    private final EmailService emailService;


    @Override
    @Transactional
    public void generateTicket(Long bookingId) {

        Booking booking = getActiveBooking(bookingId);
        validateBookingEligibleForTicket(booking);
        Payment payment = getSuccessfulPayment(bookingId);
        validateSuccessfulPayment(payment);
        validateDuplicateTicket(bookingId);
        Ticket ticket = buildTicket(booking);
        String html = ticketHtmlBuilder.build(ticket, booking);
        byte[] pdf = ticketPdfGenerator.generate(html);
        ticket.setPdfFile(pdf);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getTicketById(Long id) {

        Ticket ticket = getActiveTicket(id);
        return ticketMapper.toResponse(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getTicketByNumber(String ticketNumber) {

        Ticket ticket = ticketRepository.findByTicketNumberAndActiveTrue(ticketNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ticket not found with number: " + ticketNumber));

        return ticketMapper.toResponse(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getTicketByBookingId(Long bookingId) {

        Ticket ticket = ticketRepository
                .findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ticket not found for booking id: "
                                        + bookingId));

        return ticketMapper.toResponse(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketResponse> getAllTickets(
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

        return ticketRepository.findAllByActiveTrue(pageable)
                .map(ticketMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketResponse> searchTickets(

            TicketSearchRequest request,

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

        return ticketRepository.findAll(TicketSpecification.search(request), pageable)
                .map(ticketMapper::toResponse);
    }

    @Override
    @Transactional
    public TicketResponse updateTicket(
            Long id,
            UpdateTicketRequest request) {

        Ticket ticket = getActiveTicket(id);

        validateTicketEditable(ticket);

        if (request.getRemarks() != null) {

            ticket.setRemarks(
                    normalizeRemarks(request.getRemarks()));
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.toResponse(updatedTicket);
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {

        Ticket ticket = getTicket(id);
        validateTicketNotAlreadyDeleted(ticket);
        ticket.setActive(false);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void restoreTicket(Long id) {

        Ticket ticket = getTicket(id);
        validateTicketNotAlreadyActive(ticket);
        ticket.setActive(true);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] downloadTicket(Long bookingId) {

        Ticket ticket = ticketRepository
                .findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ticket not found for booking id: "
                                        + bookingId));

        if (ticket.getTicketStatus() == TicketStatus.CANCELLED) {

            throw new BadRequestException(
                    "Cancelled ticket cannot be downloaded.");
        }

        if (ticket.getPdfFile() == null
                || ticket.getPdfFile().length == 0) {

            throw new BadRequestException(
                    "Ticket PDF has not been generated yet.");
        }

        return ticket.getPdfFile();
    }

    @Override
    @Transactional
    public void cancelTicket(Long bookingId) {

        Ticket ticket = ticketRepository
                .findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ticket not found for booking id: "
                                        + bookingId));

        if (ticket.getTicketStatus() == TicketStatus.CANCELLED) {

            throw new BadRequestException("Ticket is already cancelled.");
        }

        ticket.setTicketStatus(TicketStatus.CANCELLED);
        ticket.setCancelledAt(LocalDateTime.now());
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public void resendTicket(Long bookingId) {

        Ticket ticket = ticketRepository
                .findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ticket not found for booking id: "
                                        + bookingId));

        if (ticket.getTicketStatus() == TicketStatus.CANCELLED) {
            throw new BadRequestException("Cancelled ticket cannot be resent.");
        }
        if(ticket.getPdfFile()==null){

            throw new BadRequestException(
                    "Ticket PDF not found."
            );
        }

        Booking booking = ticket.getBooking();
        emailService.sendTicket(booking.getPassenger().getEmail(),
                booking.getPassenger().getFirstName()
                        + " "
                        + booking.getPassenger().getLastName(),
                ticket.getPdfFile());
    }



           /*HELPER METHODS*/

    private Ticket getActiveTicket(
            Long id) {

        return ticketRepository

                .findByIdAndActiveTrue(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Ticket not found with id: "
                                        + id));
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

                "ticketNumber",

                "ticketStatus",

                "issuedAt",

                "cancelledAt"

        );

        if (!allowedFields.contains(sortBy)) {

            throw new BadRequestException(

                    "Invalid sort field: " + sortBy);
        }
    }

    private Ticket buildTicket(
            Booking booking) {

        return Ticket.builder()
                .ticketNumber(generateTicketNumber())
                .ticketStatus(TicketStatus.ISSUED)
                .issuedAt(LocalDateTime.now())
                .qrCode(generateQrCode())
                .remarks("E-Ticket Generated Successfully.")
                .booking(booking)
                .pdfFile(null)
                .active(true)
                .build();
    }

    private Booking getActiveBooking(
            Long bookingId) {

        return bookingRepository

                .findByIdAndActiveTrue(bookingId)

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Booking not found with id: "
                                        + bookingId));
    }

    private Payment getSuccessfulPayment(
            Long bookingId) {

        return paymentRepository

                .findByBookingIdAndActiveTrue(bookingId)

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Payment not found for booking id: "
                                        + bookingId));
    }

    private void validateBookingEligibleForTicket(
            Booking booking) {

        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {

            throw new BadRequestException(

                    "Ticket can only be generated for confirmed bookings.");
        }
    }

    private void validateSuccessfulPayment(
            Payment payment) {

        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {

            throw new BadRequestException(

                    "Ticket can only be generated after successful payment.");
        }
    }

    private void validateDuplicateTicket(
            Long bookingId) {

        if (ticketRepository.existsByBookingIdAndActiveTrue(
                bookingId)) {

            throw new BadRequestException(

                    "Ticket has already been generated for this booking.");
        }
    }

    private String generateTicketNumber() {

        String ticketNumber;

        do {

            ticketNumber = "TK" + System.currentTimeMillis();

        } while (ticketRepository.existsByTicketNumber(ticketNumber));

        return ticketNumber;
    }

    private String generateQrCode() {

        return "QR-" + UUID.randomUUID();
    }

    private Ticket getTicket(Long id) {

        return ticketRepository.findById(id)

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ticket not found with id: " + id));
    }

    private void validateTicketEditable(
            Ticket ticket) {

        if (!Boolean.TRUE.equals(ticket.getActive())) {

            throw new BadRequestException(
                    "Deleted ticket cannot be updated.");
        }

        if (ticket.getTicketStatus() == TicketStatus.CANCELLED) {

            throw new BadRequestException(
                    "Cancelled ticket cannot be updated.");
        }
    }

    private void validateTicketNotAlreadyDeleted(
            Ticket ticket) {

        if (!Boolean.TRUE.equals(ticket.getActive())) {

            throw new BadRequestException(
                    "Ticket is already deleted.");
        }
    }

    private void validateTicketNotAlreadyActive(
            Ticket ticket) {

        if (Boolean.TRUE.equals(ticket.getActive())) {

            throw new BadRequestException(
                    "Ticket is already active.");
        }
    }

    private String normalizeRemarks(
            String remarks) {

        if (remarks == null ||
                remarks.isBlank()) {

            return null;
        }

        return remarks.trim();
    }

}