package com.airline.airlinemanagement.controller;

import com.airline.airlinemanagement.dto.response.FlightResponse;
import com.airline.airlinemanagement.dto.request.FlightSearchRequest;
import com.airline.airlinemanagement.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(@RequestBody FlightSearchRequest request) {
        List<FlightResponse> flights = flightService.searchFlights(request);
        return ResponseEntity.ok(flights);
    }
}
