package com.vikas.airline.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightResponse {

    private Long id;

    private String flightNumber;

    private String airline;

    private Long sourceAirportId;
    private String sourceAirportCode;
    private String sourceAirportName;
    private String sourceCity;

    private Long destinationAirportId;
    private String destinationAirportCode;
    private String destinationAirportName;
    private String destinationCity;

    private LocalDate departureDate;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private BigDecimal fare;

    private Integer totalSeats;

    private Integer availableSeats;

}