package com.vikas.airline.dto.request;

import com.vikas.airline.enums.Gender;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerSearchRequest {

    private String firstName;

    private String lastName;

    private String passportNumber;

    private String email;

    private String nationality;

    private Gender gender;

    private Boolean active;
}