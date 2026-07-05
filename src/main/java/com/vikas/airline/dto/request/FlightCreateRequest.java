package com.vikas.airline.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FlightCreateRequest {
    private String flightNumber;
    private String source;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String airline;
    private int totalSeats;
    private BigDecimal farePerSeat;
}
