package com.vikas.airline.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatSummaryResponse {

    private Long flightId;

    private Integer totalSeats;

    private Long bookedSeats;

    private Long availableSeats;
}