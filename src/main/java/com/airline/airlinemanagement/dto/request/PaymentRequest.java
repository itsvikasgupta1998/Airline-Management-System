package com.airline.airlinemanagement.dto.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long bookingId;
    private String method; // e.g., "DUMMY"
}
