package com.vikas.airline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class FlightResponse {
    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private BigDecimal fare;
    private int availableSeats;
}
