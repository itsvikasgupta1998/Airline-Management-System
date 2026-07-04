package com.airline.airlinemanagement.dto.response;

import com.airline.airlinemanagement.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private String paymentReference;
    private String method;
    private PaymentStatus status;
    private double amount;
    private LocalDateTime paymentTime;
}
