package com.vikas.airline.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportResponse {

    private Long id;

    private String airportName;

    private String city;

    private String state;

    private String country;

    private String iataCode;

    private String icaoCode;

    private String timezone;

    private Boolean active;

}