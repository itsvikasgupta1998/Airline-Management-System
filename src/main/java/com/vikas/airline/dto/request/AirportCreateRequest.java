package com.vikas.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportCreateRequest {

    @NotBlank(message = "Airport name is required.")
    @Size(max = 100, message = "Airport name cannot exceed 100 characters.")
    private String airportName;

    @NotBlank(message = "City is required.")
    @Size(max = 50, message = "City cannot exceed 50 characters.")
    private String city;

    @NotBlank(message = "State is required.")
    @Size(max = 50, message = "State cannot exceed 50 characters.")
    private String state;

    @NotBlank(message = "Country is required.")
    @Size(max = 50, message = "Country cannot exceed 50 characters.")
    private String country;

    @NotBlank(message = "IATA code is required.")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "IATA code must contain exactly 3 uppercase letters."
    )
    private String iataCode;

    @NotBlank(message = "ICAO code is required.")
    @Pattern(
            regexp = "^[A-Z]{4}$",
            message = "ICAO code must contain exactly 4 uppercase letters."
    )
    private String icaoCode;

    @NotBlank(message = "Timezone is required.")
    @Size(max = 50, message = "Timezone cannot exceed 50 characters.")
    private String timezone;

}