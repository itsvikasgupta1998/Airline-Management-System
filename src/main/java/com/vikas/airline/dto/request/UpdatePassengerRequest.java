package com.vikas.airline.dto.request;

import com.vikas.airline.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePassengerRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull
    private Gender gender;

    @NotBlank
    @Size(max = 50)
    private String nationality;

    @NotBlank
    @Size(min = 6, max = 20)
    private String passportNumber;

    @NotNull
    @Future(message = "Passport expiry must be a future date")
    private LocalDate passportExpiry;

    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    private String phone;

    @Size(max = 100)
    private String guardianName;
}