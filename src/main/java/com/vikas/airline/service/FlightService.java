package com.vikas.airline.service;

import com.vikas.airline.dto.response.FlightResponse;
import com.vikas.airline.dto.request.FlightSearchRequest;

import java.util.List;

public interface FlightService {
    List<FlightResponse> searchFlights(FlightSearchRequest request);
}
