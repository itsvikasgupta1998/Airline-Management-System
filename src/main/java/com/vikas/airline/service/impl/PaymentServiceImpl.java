package com.vikas.airline.service.impl;

import com.vikas.airline.entity.Booking;
import com.vikas.airline.entity.Payment;
import com.vikas.airline.enums.PaymentMethod;
import com.vikas.airline.repository.BookingRepository;
import com.vikas.airline.repository.PaymentRepository;
import com.vikas.airline.service.PaymentService;
import com.vikas.airline.service.strategy.PaymentStrategy;
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
    public Payment makePayment(Long bookingId, PaymentMethod method) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        PaymentStrategy strategy = (PaymentStrategy) context.getBean(method.name());
        Payment payment = strategy.processPayment(booking,method);

        return paymentRepository.save(payment);
    }
}
