package com.vikas.airline.dto.request;

import com.vikas.airline.enums.TravelClass;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingRequest {

    @NotNull(message = "User id is required")
    @Positive(message = "User id must be greater than 0")
    private Long userId;

    @NotNull(message = "Flight id is required")
    @Positive(message = "Flight id must be greater than 0")
    private Long flightId;

    @NotNull(message = "Passenger id is required")
    @Positive(message = "Passenger id must be greater than 0")
    private Long passengerId;

    @NotNull(message = "Seat id is required")
    @Positive(message = "Seat id must be greater than 0")
    private Long seatId;

    @NotNull(message = "Travel class is required")
    private TravelClass travelClass;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;
}