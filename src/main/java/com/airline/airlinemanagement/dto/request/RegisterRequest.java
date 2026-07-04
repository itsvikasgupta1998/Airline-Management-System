package com.airline.airlinemanagement.dto.request;

import com.airline.airlinemanagement.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;
}
