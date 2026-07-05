package com.vikas.airline.service;

import com.vikas.airline.dto.request.FlightAssignmentRequest;
import com.vikas.airline.dto.request.FlightCreateRequest;

public interface AdminFlightService {
    String createFlight(FlightCreateRequest request);
    void assignAircraftAndCrew(FlightAssignmentRequest request);


}
