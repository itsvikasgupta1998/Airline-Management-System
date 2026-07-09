package com.vikas.airline.controller;

import com.vikas.airline.dto.request.AirportCreateRequest;
import com.vikas.airline.dto.request.AirportUpdateRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.AirportResponse;
import com.vikas.airline.service.AdminAirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/airports")
@RequiredArgsConstructor
@Tag(name = "Admin Airport", description = "Airport Management APIs")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAirportController {

    private final AdminAirportService adminAirportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Airport")
    public ResponseEntity<ApiResponse<AirportResponse>> createAirport(
            @Valid @RequestBody AirportCreateRequest request) {

        AirportResponse response = adminAirportService.createAirport(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Airport created successfully.",
                        response
                ));
    }

    @PutMapping("/{airportId}")
    @Operation(summary = "Update Airport")
    public ResponseEntity<ApiResponse<AirportResponse>> updateAirport(
            @PathVariable Long airportId,
            @Valid @RequestBody AirportUpdateRequest request) {

        AirportResponse response = adminAirportService.updateAirport(airportId, request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Airport updated successfully.",
                        response
                )
        );
    }


    @GetMapping("/{airportId}")
    @Operation(summary = "Get Airport By Id")
    public ResponseEntity<ApiResponse<AirportResponse>> getAirportById(
            @PathVariable Long airportId) {

        AirportResponse response = adminAirportService.getAirportById(airportId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Airport fetched successfully.",
                        response
                )
        );

    }

    @GetMapping
    @Operation(summary = "Get All Airports")
    public ResponseEntity<ApiResponse<Page<AirportResponse>>> getAllAirports(
            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AirportResponse> response = adminAirportService.getAllAirports(pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Airport list fetched successfully.",
                        response
                )
        );

    }

    @DeleteMapping("/{airportId}")
    @Operation(summary = "Delete Airport")
    public ResponseEntity<ApiResponse<Void>> deleteAirport(
            @PathVariable Long airportId) {

        adminAirportService.deleteAirport(airportId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Airport deleted successfully.",
                        null
                )
        );
    }

    @PatchMapping("/{airportId}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreAirport(
            @PathVariable Long airportId) {

        adminAirportService.restoreAirport(airportId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Airport restored successfully.",
                        null
                )
        );
    }

}