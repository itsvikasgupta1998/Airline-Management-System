package com.vikas.airline.dto.response;

import com.vikas.airline.enums.PaymentMethod;
import com.vikas.airline.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private String paymentReference;
    private PaymentMethod method;
    private PaymentStatus status;
    private BigDecimal amount;
    private LocalDateTime paymentTime;
}
