package com.airline.airlinemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerInfoResponse {
    private String seatNumber;
    private String fullName;
    private String email;
    private int numberOfBags;
    private double totalWeight;
    private boolean specialHandlingRequired;
}
