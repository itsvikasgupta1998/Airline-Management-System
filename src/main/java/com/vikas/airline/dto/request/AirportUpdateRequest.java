package com.vikas.airline.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportUpdateRequest {

    @Size(max = 100)
    private String airportName;

    @Size(max = 50)
    private String city;

    @Size(max = 50)
    private String state;

    @Size(max = 50)
    private String country;

    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "IATA code must contain exactly 3 uppercase letters."
    )
    private String iataCode;

    @Pattern(
            regexp = "^[A-Z]{4}$",
            message = "ICAO code must contain exactly 4 uppercase letters."
    )
    private String icaoCode;

    @Size(max = 50)
    private String timezone;

}