package com.vikas.airline.dto.request;

import com.vikas.airline.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public class UpdateUserRequest
{

    @NotBlank(message = "Full name is required.")
    @Size(max = 100, message = "Full name cannot exceed 100 characters.")
    String fullName;

    @NotNull(message = "Role is required.")
    Role role;

}






