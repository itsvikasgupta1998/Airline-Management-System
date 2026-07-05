package com.vikas.airline.dto.request;

import com.vikas.airline.enums.CrewRole;
import lombok.Data;

import java.util.List;

@Data
public class FlightAssignmentRequest {
    private Long flightId;
    private Long aircraftId;
    private List<String> crewNames;
    private CrewRole crewRole;
}
