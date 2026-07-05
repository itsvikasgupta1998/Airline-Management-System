package com.vikas.airline.dto.request;

import com.vikas.airline.enums.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequest {
    private Long bookingId;
    private PaymentMethod method;
}
