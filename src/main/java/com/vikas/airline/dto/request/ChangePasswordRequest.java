package com.vikas.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChangePasswordRequest
{

        @NotBlank(message = "Old password is required.")
        String oldPassword;

        @NotBlank(message = "New password is required.")
        @Size(min = 8, max = 100,
                message = "Password must be between 8 and 100 characters.")
        String newPassword;

        @NotBlank(message = "Confirm password is required.")
        String confirmPassword;

}