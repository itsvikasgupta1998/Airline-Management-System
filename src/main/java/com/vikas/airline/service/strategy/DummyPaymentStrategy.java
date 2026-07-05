package com.vikas.airline.service.strategy;

import com.vikas.airline.entity.Booking;
import com.vikas.airline.entity.Payment;
import com.vikas.airline.enums.PaymentMethod;
import com.vikas.airline.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component("DUMMY")
public class DummyPaymentStrategy implements PaymentStrategy {

    @Override
    public Payment processPayment(Booking booking, PaymentMethod method) {
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
