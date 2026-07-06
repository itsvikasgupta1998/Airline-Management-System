package com.vikas.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "Full name is required.")
    @Size(
            min = 3,
            max = 100,
            message = "Full name must be between 3 and 100 characters."
    )
    private String fullName;

}