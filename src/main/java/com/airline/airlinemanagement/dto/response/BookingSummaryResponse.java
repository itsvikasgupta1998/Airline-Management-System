package com.airline.airlinemanagement.dto.response;

import com.airline.airlinemanagement.entity.BookingStatus;
import com.airline.airlinemanagement.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingSummaryResponse {
    private String bookingReference;
    private String flightNumber;
    private String seatNumber;
    private String passengerName;
    private double paidAmount;
    private PaymentStatus paymentStatus;
    private BookingStatus bookingStatus;
}
