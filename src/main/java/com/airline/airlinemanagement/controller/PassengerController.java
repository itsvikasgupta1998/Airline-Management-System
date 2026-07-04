package com.airline.airlinemanagement.controller;

import com.airline.airlinemanagement.dto.request.PassengerInfoRequest;
import com.airline.airlinemanagement.dto.response.PassengerInfoResponse;
import com.airline.airlinemanagement.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping("/info")
    public ResponseEntity<PassengerInfoResponse> addPassengerInfo(@RequestBody PassengerInfoRequest request) {
        return ResponseEntity.ok(passengerService.savePassengerInfo(request));
    }
}
