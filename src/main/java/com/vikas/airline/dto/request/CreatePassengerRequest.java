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
public class CreatePassengerRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Nationality is required")
    @Size(max = 50)
    private String nationality;

    @NotBlank(message = "Passport number is required")
    @Size(min = 6, max = 20, message = "Passport number must be between 6 and 20 characters")
    private String passportNumber;

    @NotNull(message = "Passport expiry is required")
    @Future(message = "Passport expiry must be a future date")
    private LocalDate passportExpiry;

    @Email(message = "Invalid email format")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    private String phone;

    @Size(max = 100)
    private String guardianName;
}