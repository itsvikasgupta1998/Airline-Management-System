package com.vikas.airline.service;

import com.vikas.airline.entity.Payment;
import com.vikas.airline.enums.PaymentMethod;

public interface PaymentService {

    Payment makePayment(
            Long bookingId,
            PaymentMethod request
    );

}
