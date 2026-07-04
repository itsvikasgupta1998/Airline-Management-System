package com.airline.airlinemanagement.service;

import com.airline.airlinemanagement.entity.Payment;

public interface PaymentService {
    Payment makePayment(Long bookingId, String method);
}
