package com.vikas.airline.dto.response;

import com.vikas.airline.enums.BookingStatus;
import com.vikas.airline.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BookingSummaryResponse {
    private String bookingReference;
    private String flightNumber;
    private String seatNumber;
    private String passengerName;
    private BigDecimal paidAmount;
    private PaymentStatus paymentStatus;
    private BookingStatus bookingStatus;
}
