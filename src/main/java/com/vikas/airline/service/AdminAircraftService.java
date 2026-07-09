package com.vikas.airline.service;

import com.vikas.airline.dto.request.AircraftCreateRequest;
import com.vikas.airline.dto.request.AircraftUpdateRequest;
import com.vikas.airline.dto.response.AircraftResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminAircraftService {

    AircraftResponse createAircraft(AircraftCreateRequest request);

    AircraftResponse updateAircraft(
            Long aircraftId,
            AircraftUpdateRequest request);

    AircraftResponse getAircraftById(Long aircraftId);

    Page<AircraftResponse> getAllAircraft(Pageable pageable);

    void deleteAircraft(Long aircraftId);

    void restoreAircraft(Long aircraftId);
}