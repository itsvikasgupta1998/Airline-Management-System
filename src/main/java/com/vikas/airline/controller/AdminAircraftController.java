package com.vikas.airline.controller;

import com.vikas.airline.dto.request.AircraftCreateRequest;
import com.vikas.airline.dto.request.AircraftUpdateRequest;
import com.vikas.airline.dto.response.AircraftResponse;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.service.AdminAircraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequiredArgsConstructor
@RequestMapping("/api/admin/aircraft")
@Tag(name = "Admin Aircraft APIs", description = "Aircraft Management APIs")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAircraftController {

    private final AdminAircraftService aircraftService;

    @PostMapping
    @Operation(summary = "Create Aircraft")
    public ResponseEntity<ApiResponse<AircraftResponse>> createAircraft(
            @Valid @RequestBody AircraftCreateRequest request) {

        AircraftResponse response = aircraftService.createAircraft(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Aircraft created successfully.",
                        response
                ));
    }

    @PutMapping("/{aircraftId}")
    @Operation(summary = "Update Aircraft")
    public ResponseEntity<ApiResponse<AircraftResponse>> updateAircraft(
            @PathVariable Long aircraftId,
            @Valid @RequestBody AircraftUpdateRequest request) {

        AircraftResponse response = aircraftService.updateAircraft(aircraftId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Aircraft updated successfully.",
                        response
                )
        );
    }

    @GetMapping("/{aircraftId}")
    @Operation(summary = "Get Aircraft By Id")
    public ResponseEntity<ApiResponse<AircraftResponse>> getAircraftById(
            @PathVariable Long aircraftId) {

        AircraftResponse response = aircraftService.getAircraftById(aircraftId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Aircraft fetched successfully.",
                        response
                )
        );
    }

    @GetMapping
    @Operation(summary = "Get All Aircraft")
    public ResponseEntity<ApiResponse<Page<AircraftResponse>>> getAllAircraft(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AircraftResponse> response = aircraftService.getAllAircraft(pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Aircraft list fetched successfully.",
                        response
                )
        );
    }

    @DeleteMapping("/{aircraftId}")
    @Operation(summary = "Delete Aircraft")
    public ResponseEntity<ApiResponse<Void>> deleteAircraft(
            @PathVariable Long aircraftId) {

        aircraftService.deleteAircraft(aircraftId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Aircraft deleted successfully.",
                        null
                )
        );
    }

    @PatchMapping("/{aircraftId}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreAircraft(
            @PathVariable Long aircraftId) {

        aircraftService.restoreAircraft(aircraftId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Aircraft restored successfully.",
                        null
                )
        );
    }
}