package com.vikas.airline.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightUpdateRequest {

    private Long aircraftId;

    private Long sourceAirportId;

    private Long destinationAirportId;

    @FutureOrPresent
    private LocalDate departureDate;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    @Size(max = 100)
    private String airline;

    @DecimalMin("1.0")
    private BigDecimal fare;

}