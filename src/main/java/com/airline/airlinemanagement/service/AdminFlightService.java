package com.airline.airlinemanagement.service;

import com.airline.airlinemanagement.dto.request.FlightAssignmentRequest;
import com.airline.airlinemanagement.dto.request.FlightCreateRequest;

public interface AdminFlightService {
    String createFlight(FlightCreateRequest request);
    void assignAircraftAndCrew(FlightAssignmentRequest request);


}
