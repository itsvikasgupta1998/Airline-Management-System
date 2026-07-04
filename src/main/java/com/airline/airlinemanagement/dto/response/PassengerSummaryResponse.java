package com.airline.airlinemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerSummaryResponse {
    private String passengerName;
    private String email;
    private String contactNumber;
    private String seatNumber;
    private int bags;
    private double totalWeight;
}
