package com.airline.airlinemanagement.service.impl;

import com.airline.airlinemanagement.entity.Booking;
import com.airline.airlinemanagement.entity.Payment;
import com.airline.airlinemanagement.repository.BookingRepository;
import com.airline.airlinemanagement.repository.PaymentRepository;
import com.airline.airlinemanagement.service.PaymentService;
import com.airline.airlinemanagement.service.strategy.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final ApplicationContext context;

    @Override
    @Transactional
    public Payment makePayment(Long bookingId, String method) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        PaymentStrategy strategy = (PaymentStrategy) context.getBean(method.toUpperCase());
        Payment payment = strategy.processPayment(booking, method.toUpperCase());

        return paymentRepository.save(payment);
    }
}
