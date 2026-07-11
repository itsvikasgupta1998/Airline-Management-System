package com.vikas.airline.dto.response;

import com.vikas.airline.enums.BookingStatus;
import com.vikas.airline.enums.PaymentStatus;
import com.vikas.airline.enums.TravelClass;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private String bookingReference;

    private BookingStatus bookingStatus;

    private PaymentStatus paymentStatus;

    private TravelClass travelClass;

    private LocalDateTime bookingDate;

    private BigDecimal baseFare;

    private BigDecimal tax;

    private BigDecimal serviceFee;

    private BigDecimal discount;

    private BigDecimal totalFare;

    private String currency;

    private String remarks;

    private Long userId;

    private Long flightId;

    private Long passengerId;

    private Long seatId;

    private BigDecimal refundAmount;

    private LocalDateTime cancelledAt;

    private String cancellationReason;
}