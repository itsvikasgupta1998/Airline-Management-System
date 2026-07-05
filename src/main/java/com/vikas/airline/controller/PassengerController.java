package com.vikas.airline.controller;

import com.vikas.airline.dto.request.PassengerInfoRequest;
import com.vikas.airline.dto.response.PassengerInfoResponse;
import com.vikas.airline.service.PassengerService;
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
