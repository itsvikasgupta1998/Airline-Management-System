package com.vikas.airline.controller;

import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.SeatResponse;
import com.vikas.airline.dto.response.SeatSummaryResponse;
import com.vikas.airline.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/flights/{flightId}/seats")
@RequiredArgsConstructor
@Validated
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getAllSeats(
            @PathVariable Long flightId) {

        List<SeatResponse> response = seatService.getAllSeats(flightId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Seats fetched successfully.",
                        response
                )
        );
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getAvailableSeats(
            @PathVariable Long flightId) {

        List<SeatResponse> response = seatService.getAvailableSeats(flightId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Available seats fetched successfully.",
                        response
                )
        );
    }

    @GetMapping("/booked")
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getBookedSeats(
            @PathVariable Long flightId) {

        List<SeatResponse> response = seatService.getBookedSeats(flightId);
        return ResponseEntity.ok(ApiResponse.success(
                "Booked seats fetched successfully.",response));
    }

    @GetMapping("/{seatNumber}")
    public ResponseEntity<ApiResponse<SeatResponse>> getSeat(
            @PathVariable Long flightId,
            @PathVariable String seatNumber) {

        SeatResponse response = seatService.getSeatBySeatNumber(flightId, seatNumber);

        return ResponseEntity.ok(ApiResponse.success(
                "Seat fetched successfully.",response));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<SeatSummaryResponse>> getSummary(
            @PathVariable Long flightId) {

        SeatSummaryResponse response = seatService.getSeatSummary(flightId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Seat summary fetched successfully.",
                        response));
    }
}