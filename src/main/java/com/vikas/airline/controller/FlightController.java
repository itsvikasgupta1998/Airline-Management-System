package com.vikas.airline.controller;

import com.vikas.airline.dto.request.FlightSearchRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.FlightResponse;
import com.vikas.airline.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Validated
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Page<FlightResponse>>> searchFlights(

            @Valid @RequestBody FlightSearchRequest request,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "departureDate") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Page<FlightResponse> response =
                flightService.searchFlights(
                        request,
                        page,
                        size,
                        sortBy,
                        sortDir
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Flights fetched successfully.",
                        response
                )
        );
    }
}