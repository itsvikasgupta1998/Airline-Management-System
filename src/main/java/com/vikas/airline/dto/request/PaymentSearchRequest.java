package com.vikas.airline.dto.request;

import com.vikas.airline.enums.PaymentMethod;
import com.vikas.airline.enums.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentSearchRequest {

    private String transactionId;

    private Long bookingId;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String currency;

    private Boolean active;
}