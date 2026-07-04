package com.airline.airlinemanagement.controller;

import com.airline.airlinemanagement.dto.request.FlightAssignmentRequest;
import com.airline.airlinemanagement.dto.request.FlightCreateRequest;
import com.airline.airlinemanagement.service.AdminFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/flights")
@RequiredArgsConstructor
public class AdminFlightController {

    private final AdminFlightService flightService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createFlight(@RequestBody FlightCreateRequest request) {
        return ResponseEntity.ok(flightService.createFlight(request));
    }

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignFlightDetails(@RequestBody FlightAssignmentRequest request) {
        flightService.assignAircraftAndCrew(request);
        return ResponseEntity.ok("Aircraft and Crew assigned successfully.");
    }




}
