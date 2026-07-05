package com.vikas.airline.dto.request;

import com.vikas.airline.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerInfoRequest {
    private Long seatId;
    private String fullName;
    private String email;
    private String contactNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private int numberOfBags;
    private double totalWeight;
    private boolean specialHandlingRequired;
    private String specialNotes;
}
