package com.vikas.airline.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundPaymentRequest {

    @NotNull(message = "Refund amount is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Refund amount must be greater than zero")
    private BigDecimal refundAmount;

    @Size(max = 500,
            message = "Refund reason cannot exceed 500 characters")
    private String refundReason;
}