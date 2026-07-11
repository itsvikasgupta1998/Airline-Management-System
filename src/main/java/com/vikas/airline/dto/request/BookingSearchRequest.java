package com.vikas.airline.dto.request;

import com.vikas.airline.enums.BookingStatus;
import com.vikas.airline.enums.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookingSearchRequest {

    private String bookingReference;

    private Long flightId;

    private Long passengerId;

    private Long userId;

    private BookingStatus bookingStatus;

    private PaymentStatus paymentStatus;

    private Boolean active;
}