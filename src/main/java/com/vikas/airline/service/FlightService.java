package com.vikas.airline.service;


import com.vikas.airline.dto.request.FlightSearchRequest;
import com.vikas.airline.dto.response.FlightResponse;
import org.springframework.data.domain.Page;

public interface FlightService {
    Page<FlightResponse> searchFlights(
            FlightSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir
    );
}
