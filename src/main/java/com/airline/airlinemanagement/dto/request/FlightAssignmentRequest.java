package com.airline.airlinemanagement.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FlightAssignmentRequest {
    private Long flightId;
    private Long aircraftId;
    private List<String> crewNames;
    private List<String> crewRoles;
}
