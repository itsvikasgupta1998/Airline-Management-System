package com.vikas.airline.controller;

import com.vikas.airline.dto.request.CreatePassengerRequest;
import com.vikas.airline.dto.request.PassengerSearchRequest;
import com.vikas.airline.dto.request.UpdatePassengerRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.PassengerResponse;
import com.vikas.airline.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
@Validated
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<ApiResponse<PassengerResponse>> createPassenger(
            @Valid @RequestBody CreatePassengerRequest request) {

        PassengerResponse response = passengerService.createPassenger(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Passenger created successfully.",
                        response));
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<ApiResponse<PassengerResponse>> getPassengerById(
            @PathVariable Long passengerId) {

        PassengerResponse response = passengerService.getPassengerById(passengerId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Passenger retrieved successfully.", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PassengerResponse>>> getAllPassengers(

            @RequestParam(defaultValue = "0")   @Min(0) int page,
            @RequestParam(defaultValue = "10")   @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<PassengerResponse> response = passengerService.getAllPassengers(
                        page,
                        size,
                        sortBy,
                        sortDir);

        return ResponseEntity.ok(ApiResponse.success(
                        "Passengers retrieved successfully.", response));
    }

    @PutMapping("/{passengerId}")
    public ResponseEntity<ApiResponse<PassengerResponse>> updatePassenger(

            @PathVariable Long passengerId,
            @Valid
            @RequestBody
            UpdatePassengerRequest request) {

        PassengerResponse response = passengerService.updatePassenger(
                        passengerId,
                        request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Passenger updated successfully.", response));
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<ApiResponse<Void>> deletePassenger(
            @PathVariable Long passengerId) {

        passengerService.deletePassenger(passengerId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Passenger deleted successfully.",
                        null));
    }

    @PatchMapping("/{passengerId}/restore")
    public ResponseEntity<ApiResponse<Void>> restorePassenger(
            @PathVariable Long passengerId) {

        passengerService.restorePassenger(passengerId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Passenger restored successfully.",
                        null
                )
        );
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PassengerResponse>>> searchPassengers(

            PassengerSearchRequest request,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Passengers retrieved successfully.",
                        passengerService.searchPassengers(
                                request,
                                page,
                                size,
                                sortBy,
                                sortDir)));

    }

}