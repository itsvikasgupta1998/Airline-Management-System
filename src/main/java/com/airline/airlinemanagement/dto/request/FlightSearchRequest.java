package com.airline.airlinemanagement.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightSearchRequest {
    private String source;
    private String destination;
    private LocalDate departureDate;
}
