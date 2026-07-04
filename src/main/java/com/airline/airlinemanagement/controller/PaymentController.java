package com.airline.airlinemanagement.controller;

import com.airline.airlinemanagement.dto.request.PaymentRequest;
import com.airline.airlinemanagement.dto.response.PaymentResponse;
import com.airline.airlinemanagement.entity.Payment;
import com.airline.airlinemanagement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest request) {
        Payment payment = paymentService.makePayment(request.getBookingId(), request.getMethod());

        return ResponseEntity.ok(new PaymentResponse(
                payment.getPaymentReference(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getAmount(),
                payment.getPaymentTime()
        ));
    }
}
