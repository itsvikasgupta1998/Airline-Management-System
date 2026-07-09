package com.vikas.airline.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightCreateRequest {

    @NotNull(message = "Aircraft is required.")
    private Long aircraftId;

    @NotBlank(message = "Flight number is required.")
    @Size(max = 20)
    private String flightNumber;

    @NotNull
    private Long sourceAirportId;

    @NotNull
    private Long destinationAirportId;

    @NotNull(message = "Departure date is required.")
    @FutureOrPresent(message = "Departure date cannot be in the past.")
    private LocalDate departureDate;

    @NotNull(message = "Departure time is required.")
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is required.")
    private LocalTime arrivalTime;

    @NotBlank(message = "Airline is required.")
    @Size(max = 100)
    private String airline;

    @NotNull(message = "Total seats are required.")
    @Min(value = 1, message = "Total seats must be greater than zero.")
    private Integer totalSeats;

    @NotNull(message = "Fare is required.")
    @DecimalMin(value = "1.0", message = "Fare must be greater than zero.")
    private BigDecimal farePerSeat;

}