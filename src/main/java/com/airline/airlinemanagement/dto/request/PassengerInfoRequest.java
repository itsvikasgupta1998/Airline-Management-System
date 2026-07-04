package com.airline.airlinemanagement.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerInfoRequest {
    private Long seatId;
    private String fullName;
    private String email;
    private String contactNumber;
    private LocalDate dateOfBirth;
    private String gender;

    private int numberOfBags;
    private double totalWeight;
    private boolean specialHandlingRequired;
    private String specialNotes;
}
