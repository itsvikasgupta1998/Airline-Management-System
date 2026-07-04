package com.airline.airlinemanagement.service;

import com.airline.airlinemanagement.dto.response.FlightResponse;
import com.airline.airlinemanagement.dto.request.FlightSearchRequest;

import java.util.List;

public interface FlightService {
    List<FlightResponse> searchFlights(FlightSearchRequest request);
}
