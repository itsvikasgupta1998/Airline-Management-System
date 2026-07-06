package com.vikas.airline.dto.request;

import com.vikas.airline.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRoleRequest {

    @NotNull(message = "Role is required.")
    private Role role;

}