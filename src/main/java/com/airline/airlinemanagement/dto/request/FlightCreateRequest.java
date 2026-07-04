package com.airline.airlinemanagement.dto.request;

import lombok.Data;

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
    private double farePerSeat;
}
