package com.vikas.airline.service.strategy;

import com.vikas.airline.entity.Booking;
import com.vikas.airline.entity.Payment;
import com.vikas.airline.enums.PaymentMethod;

public interface PaymentStrategy {
    Payment processPayment(Booking booking, PaymentMethod paymentMethod);
}
