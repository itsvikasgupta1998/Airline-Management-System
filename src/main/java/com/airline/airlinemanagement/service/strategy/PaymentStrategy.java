package com.airline.airlinemanagement.service.strategy;

import com.airline.airlinemanagement.entity.Booking;
import com.airline.airlinemanagement.entity.Payment;

public interface PaymentStrategy {
    Payment processPayment(Booking booking, String paymentMethod);
}
