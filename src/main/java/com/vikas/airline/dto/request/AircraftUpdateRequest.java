package com.vikas.airline.dto.request;

import com.vikas.airline.enums.AircraftStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftUpdateRequest {

    @Size(max = 100)
    private String model;

    @Size(max = 100)
    private String manufacturer;

    @Min(1)
    private Integer capacity;

    private AircraftStatus status;
}