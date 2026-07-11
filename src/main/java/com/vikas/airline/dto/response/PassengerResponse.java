package com.vikas.airline.dto.response;

import com.vikas.airline.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String fullName;

    private LocalDate dateOfBirth;

    private Integer age;

    private Gender gender;

    private String nationality;

    private String passportNumber;

    private LocalDate passportExpiry;

    private String email;

    private String phone;

    private String guardianName;
}