package com.vikas.airline.service;

import com.vikas.airline.dto.request.FlightAssignmentRequest;
import com.vikas.airline.dto.request.FlightCreateRequest;
import com.vikas.airline.dto.request.FlightUpdateRequest;
import com.vikas.airline.dto.response.FlightResponse;

public interface AdminFlightService {

    FlightResponse createFlight(FlightCreateRequest request);

    FlightResponse updateFlight(
            Long flightId,
            FlightUpdateRequest request);

    void assignAircraftAndCrew(FlightAssignmentRequest request);

    void deleteFlight(Long flightId);

    void restoreFlight(Long flightId);

}