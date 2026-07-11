package com.vikas.airline.controller;

import com.vikas.airline.dto.request.FlightCreateRequest;
import com.vikas.airline.dto.request.FlightUpdateRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.FlightResponse;
import com.vikas.airline.service.AdminFlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/flights")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasRole('ADMIN')")
public class AdminFlightController {

    private final AdminFlightService adminFlightService;

    @PostMapping
    public ResponseEntity<ApiResponse<FlightResponse>> createFlight(
            @Valid @RequestBody FlightCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Flight created successfully.",
                        adminFlightService.createFlight(request)));
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<ApiResponse<FlightResponse>> updateFlight(

            @PathVariable Long flightId,

            @Valid @RequestBody FlightUpdateRequest request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Flight updated successfully.",
                        adminFlightService.updateFlight(
                                flightId,
                                request
                        )
                )
        );
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(
            @PathVariable Long flightId) {

        adminFlightService.deleteFlight(flightId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Flight deleted successfully.",
                        null
                )
        );
    }

    @PatchMapping("/{flightId}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> restoreFlight(
            @PathVariable Long flightId) {

        adminFlightService.restoreFlight(flightId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Flight restored successfully.",
                        null
                )
        );
    }
}


