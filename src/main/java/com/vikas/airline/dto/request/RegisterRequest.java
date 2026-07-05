package com.vikas.airline.dto.request;

import com.vikas.airline.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request object for user registration")
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    @Schema(example = "Vikas Gupta")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Schema(example = "vikas@gmail.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",
            message = "Password must contain uppercase, lowercase, digit and special character"
    )
    @Schema(example = "Vikas@123")
    private String password;

    @NotNull(message = "Role is required")
    @Schema(example = "ROLE_USER")
    private Role role;

}