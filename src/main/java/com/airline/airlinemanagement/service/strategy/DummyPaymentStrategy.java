package com.airline.airlinemanagement.service.strategy;

import com.airline.airlinemanagement.entity.Booking;
import com.airline.airlinemanagement.entity.Payment;
import com.airline.airlinemanagement.entity.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component("DUMMY")
public class DummyPaymentStrategy implements PaymentStrategy {

    @Override
    public Payment processPayment(Booking booking, String method) {
        return Payment.builder()
                .paymentReference(UUID.randomUUID().toString())
                .method(method)
                .amount(booking.getTotalFare())
                .paymentTime(LocalDateTime.now())
                .status(PaymentStatus.SUCCESS)
                .booking(booking)
                .build();
    }
}
