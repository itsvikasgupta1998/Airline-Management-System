package com.vikas.airline.dto.request;

import com.vikas.airline.enums.PaymentMethod;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePaymentRequest {

    private PaymentMethod paymentMethod;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;
}