package com.vikas.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelBookingRequest {

    @NotBlank(message = "Cancellation reason is required")
    @Size(max = 500)
    private String cancellationReason;
}