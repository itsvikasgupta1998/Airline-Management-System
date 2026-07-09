package com.vikas.airline.dto.request;

import com.vikas.airline.enums.AircraftStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftCreateRequest {

    @NotBlank(message = "Registration number is required.")
    @Size(max = 20)
    @Pattern(
            regexp = "^[A-Z0-9-]+$",
            message = "Registration number can contain only uppercase letters, numbers and hyphen."
    )
    private String registrationNumber;

    @NotBlank(message = "Aircraft model is required.")
    @Size(max = 100)
    private String model;

    @NotBlank(message = "Manufacturer is required.")
    @Size(max = 100)
    private String manufacturer;

    @NotNull(message = "Capacity is required.")
    @Min(value = 1, message = "Capacity must be greater than zero.")
    private Integer capacity;

    @NotNull(message = "Aircraft status is required.")
    private AircraftStatus status;
}