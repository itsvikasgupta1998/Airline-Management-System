package com.vikas.airline.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchRequest {

    @NotBlank(message = "Source is required.")
    private String source;

    @NotBlank(message = "Destination is required.")
    private String destination;

    @NotNull(message = "Departure date is required.")
    private LocalDate departureDate;


    @Size(max = 100, message = "Airline cannot exceed 100 characters.")
    private String airline;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Minimum fare cannot be negative."
    )
    private BigDecimal minimumFare;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Maximum fare cannot be negative."
    )
    private BigDecimal maximumFare;

}