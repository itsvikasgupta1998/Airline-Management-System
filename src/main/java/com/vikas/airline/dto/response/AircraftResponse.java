package com.vikas.airline.dto.response;

import com.vikas.airline.enums.AircraftStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftResponse {

    private Long id;

    private String registrationNumber;

    private String model;

    private String manufacturer;

    private Integer capacity;

    private AircraftStatus status;
}