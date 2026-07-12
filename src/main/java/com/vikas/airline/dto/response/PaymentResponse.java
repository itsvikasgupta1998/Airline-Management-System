package com.vikas.airline.dto.response;

import com.vikas.airline.enums.PaymentMethod;
import com.vikas.airline.enums.PaymentStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;

    private String transactionId;

    private String gatewayReference;

    private Long bookingId;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private BigDecimal amount;

    private String currency;

    private BigDecimal refundAmount;

    private LocalDateTime paidAt;

    private LocalDateTime refundProcessedAt;

    private String remarks;
}