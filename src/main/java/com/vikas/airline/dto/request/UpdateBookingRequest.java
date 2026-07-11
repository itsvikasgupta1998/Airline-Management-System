package com.vikas.airline.dto.request;

import com.vikas.airline.enums.TravelClass;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookingRequest {

    private Long seatId;

    private TravelClass travelClass;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;
}