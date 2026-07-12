package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.CreatePaymentRequest;
import com.vikas.airline.dto.request.PaymentSearchRequest;
import com.vikas.airline.dto.request.RefundPaymentRequest;
import com.vikas.airline.dto.request.UpdatePaymentRequest;
import com.vikas.airline.dto.response.PaymentResponse;
import com.vikas.airline.entity.Booking;
import com.vikas.airline.entity.Payment;
import com.vikas.airline.enums.BookingStatus;
import com.vikas.airline.enums.NotificationType;
import com.vikas.airline.enums.PaymentStatus;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.PaymentMapper;
import com.vikas.airline.repository.BookingRepository;
import com.vikas.airline.repository.PaymentRepository;
import com.vikas.airline.service.NotificationService;
import com.vikas.airline.service.PaymentService;
import com.vikas.airline.service.TicketService;
import com.vikas.airline.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;
    private final TicketService ticketService;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public PaymentResponse createPayment(
            CreatePaymentRequest request) {

        Booking booking = getActiveBooking(request.getBookingId());
        validateBookingEligibleForPayment(booking);
        validateDuplicatePayment(booking);
        Payment payment = buildPayment(booking, request);

        Payment savedPayment = paymentRepository.save(payment);
        // Generate E-Ticket after successful payment
        ticketService.generateTicket(savedPayment.getBooking().getId());
        notificationService.createAndSendNotification(

                savedPayment.getBooking().getUser().getId(),
                savedPayment.getBooking().getId(),
                savedPayment.getBooking()
                        .getPassenger()
                        .getEmail(),

                "Payment Successful",

                """
                Dear %s,
        
                Your payment has been received successfully.
        
                Booking Reference : %s
        
                Transaction Id : %s
        
                Thank you for choosing our Airline.
                """
                        .formatted(
                                savedPayment.getBooking()
                                        .getPassenger()
                                        .getFirstName()
                                        + " "
                                        + booking.getPassenger().getLastName(),

                                savedPayment.getBooking()
                                        .getBookingReference(),

                                savedPayment.getTransactionId()),

                NotificationType.EMAIL
        );
        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    @Transactional
    public PaymentResponse updatePayment(Long id, UpdatePaymentRequest request) {

        Payment payment = getActivePayment(id);
        validatePaymentEditable(payment);

        if (request.getPaymentMethod() != null) {
            payment.setPaymentMethod(request.getPaymentMethod());
        }

        if (request.getRemarks() != null) {
            payment.setRemarks(normalizeRemarks(request.getRemarks()));
        }

        Payment updatedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(updatedPayment);
    }


    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(
            Long id) {

        Payment payment = getActivePayment(id);

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getAllPayments(
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

        return paymentRepository
                .findAllByActiveTrue(pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> searchPayments(

            PaymentSearchRequest request,

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

        return paymentRepository.findAll(

                        PaymentSpecification.search(request),

                        pageable)

                .map(paymentMapper::toResponse);
    }

    @Override
    @Transactional
    public PaymentResponse refundPayment(Long id, RefundPaymentRequest request) {

        Payment payment = getActivePayment(id);

        validateBookingCancelled(payment.getBooking());

        processRefund(
                payment,
                request.getRefundAmount(),
                request.getRefundReason());

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {

        Payment payment = getPayment(id);

        validatePaymentNotAlreadyDeleted(payment);

        validatePaymentNotRefunded(payment);

        payment.setActive(false);

        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void restorePayment(Long id) {

        Payment payment = getPayment(id);

        validatePaymentNotAlreadyActive(payment);

        validatePaymentNotRefunded(payment);

        payment.setActive(true);

        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void processRefund(Long bookingId, String refundReason) {

        Payment payment = paymentRepository.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(

                                "Payment not found for booking id: "
                                        + bookingId));

        processRefund(payment, payment.getAmount(), refundReason);

    }

                /*HELPER METHODS*/

    private Booking getActiveBooking(Long id) {

        return bookingRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Booking not found with id: " + id));
    }

    private Payment buildPayment(
            Booking booking,
            CreatePaymentRequest request) {

        return Payment.builder()

                .transactionId(generateTransactionId())

                .gatewayReference(generateGatewayReference())

                .booking(booking)

                .paymentMethod(request.getPaymentMethod())

                .paymentStatus(PaymentStatus.SUCCESS)

                .amount(booking.getTotalFare())

                .currency("INR")

                .paidAt(LocalDateTime.now())

                .remarks(normalizeRemarks(
                        request.getRemarks()))

                .active(true)

                .build();
    }

    private void processRefund(
            Payment payment,
            BigDecimal refundAmount,
            String refundReason) {

        validatePaymentNotRefunded(payment);
        validateRefundAmount(payment, refundAmount);
        payment.setRefundAmount(refundAmount);
        payment.setRefundReason(refundReason);
        payment.setRefundProcessedAt(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);
        Booking booking = payment.getBooking();

        notificationService.createAndSendNotification(
                booking.getUser().getId(),
                booking.getId(),
                booking.getPassenger().getEmail(),
                "Refund Processed",
                """
                Dear %s,
        
                Your refund has been processed successfully.
        
                Booking Reference : %s
        
                Refund Amount : %s
        
                Thank you.
                """
                        .formatted(
                                booking.getPassenger().getFirstName()
                                        + " "
                                        + booking.getPassenger().getLastName(),
                                booking.getBookingReference(),
                                refundAmount
                        ),
                NotificationType.EMAIL
        );

        // Cancel generated ticket
        ticketService.cancelTicket(booking.getId());
    }

    private String normalizeRemarks(
            String remarks) {

        if (remarks == null ||
                remarks.isBlank()) {

            return null;
        }

        return remarks.trim();
    }

    private void validatePaymentEditable(
            Payment payment) {

        if (payment.getPaymentStatus() == PaymentStatus.REFUNDED
                || payment.getPaymentStatus() == PaymentStatus.PARTIALLY_REFUNDED) {

            throw new BadRequestException(
                    "Refunded payment cannot be updated.");
        }

        if (!Boolean.TRUE.equals(payment.getActive())) {

            throw new BadRequestException(
                    "Deleted payment cannot be updated.");
        }
    }

    private Payment getPayment(Long id) {

        return paymentRepository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Payment not found with id: " + id));
    }

    private Payment getActivePayment(Long id) {

        return paymentRepository
                .findByIdAndActiveTrue(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Payment not found with id: " + id));
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

                "transactionId",

                "paymentStatus",

                "paymentMethod",

                "amount",

                "paidAt"
        );

        if (!allowedFields.contains(sortBy)) {

            throw new BadRequestException(
                    "Invalid sort field: " + sortBy);
        }
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

    private void validatePaymentNotAlreadyDeleted(
            Payment payment) {

        if (!Boolean.TRUE.equals(payment.getActive())) {

            throw new BadRequestException(
                    "Payment is already deleted.");
        }
    }

    private void validatePaymentNotAlreadyActive(
            Payment payment) {

        if (Boolean.TRUE.equals(payment.getActive())) {

            throw new BadRequestException(
                    "Payment is already active.");
        }
    }


    private void validatePaymentNotRefunded(
            Payment payment) {

        if (payment.getPaymentStatus() == PaymentStatus.REFUNDED) {

            throw new BadRequestException("Payment has already been refunded.");
        }
        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {

            throw new BadRequestException("Only successful payments can be refunded.");
        }
    }

    private void validateBookingCancelled(
            Booking booking) {

        if (booking.getBookingStatus() != BookingStatus.CANCELLED) {

            throw new BadRequestException(
                    "Refund is allowed only for cancelled bookings.");
        }
    }

    private String generateTransactionId() {

        String transactionId;

        do {

            transactionId = "TXN" + System.currentTimeMillis();

        } while (paymentRepository.existsByTransactionId(transactionId));

        return transactionId;
    }

    private String generateGatewayReference() {

        return "PGW-" + UUID.randomUUID().toString().replace("-", "");
    }

    private void validateBookingEligibleForPayment(
            Booking booking) {

        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {

            throw new BadRequestException("Payment can only be made for confirmed bookings.");
        }
    }

    private void validateRefundAmount(
            Payment payment,
            BigDecimal refundAmount) {

        if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new BadRequestException(
                    "Refund amount must be greater than zero.");
        }

        if (refundAmount.compareTo(payment.getAmount()) > 0) {

            throw new BadRequestException(
                    "Refund amount cannot exceed payment amount.");
        }
    }

    private void validateDuplicatePayment(Booking booking) {

        if (paymentRepository.existsByBookingIdAndPaymentStatus(
                booking.getId(),
                PaymentStatus.SUCCESS)) {

            throw new BadRequestException(
                    "Payment has already been completed for this booking.");
        }
    }

}