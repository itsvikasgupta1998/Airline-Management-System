package com.vikas.airline.service;

import com.vikas.airline.dto.request.AirportCreateRequest;
import com.vikas.airline.dto.request.AirportUpdateRequest;
import com.vikas.airline.dto.response.AirportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminAirportService {

    AirportResponse createAirport(
            AirportCreateRequest request
    );

    AirportResponse updateAirport(
            Long airportId,
            AirportUpdateRequest request
    );

    AirportResponse getAirportById(Long airportId);

    Page<AirportResponse> getAllAirports(Pageable pageable);

    void deleteAirport(Long airportId);

    void restoreAirport(Long airportId);

}