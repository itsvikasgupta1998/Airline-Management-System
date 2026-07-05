package com.vikas.airline.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request object for login")
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    @Schema(example = "vikas@gmail.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(example = "Vikas@123")
    private String password;

}